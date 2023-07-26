export const SET_CURRENCY = "SET_CURRENCY";

export const setCurrency = (currencyName) => {
  return (dispatch) => {
    let currencyRate = 0;

    // Thực hiện chuyển đổi tỷ giá theo tên tiền tệ
    if (currencyName === "VNĐ") {
      currencyRate = 1; // Giá trị tỷ giá từ USD sang VNĐ
    } else if (currencyName === "GBP") {
      currencyRate = 1 / 26780; // Giá trị tỷ giá từ GBP sang VNĐ
    } else if (currencyName === "EUR") {
      currencyRate = 1 / 25600; // Giá trị tỷ giá từ EUR sang VNĐ
    } else if (currencyName === "USD") {
      currencyRate = 1 / 22750; // Giá trị tỷ giá từ EUR sang VNĐ
    }
    dispatch({
      type: SET_CURRENCY,
      payload: { currencyName, currencyRate },
    });
  };
};
