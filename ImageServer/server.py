from flask import Flask, request, jsonify, render_template,send_from_directory
import os

app = Flask(__name__)

# Đường dẫn tới thư mục chứa ảnh
IMAGE_DIRECTORY = 'C:/Users/golos/Desktop/ImageServer/images'

ALLOWED_EXTENSIONS = {'jpg', 'jpeg', 'png','webp'}


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route('/images/<path:image_name>')
def get_image(image_name):
    # Trả về ảnh từ đường dẫn
    return send_from_directory(IMAGE_DIRECTORY, image_name)


@app.route('/upload', methods=['GET', 'POST'])
def upload():
    if request.method == 'POST':
        if 'images' not in request.files:
            return jsonify({'error': 'No images uploaded.'}), 400
        
        image_files = request.files.getlist('images')
        
        for image_file in image_files:
            if allowed_file(image_file.filename):
                image_path = os.path.join(IMAGE_DIRECTORY, image_file.filename)
                image_file.save(image_path)
            return jsonify({'error': 'File type is not support.'}),400
        
        return jsonify({'success': 'Images uploaded successfully.'}),200
    
    return render_template('upload.html')


if __name__ == '__main__':
    app.run()
