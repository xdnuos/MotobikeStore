// import { FETCH_PRODUCTS_SUCCESS } from "../actions/productActions";

// const initState = {
//   products: [],
// };

// const productReducer = (state = initState, action) => {
//   if (action.type === FETCH_PRODUCTS_SUCCESS) {
//     return {
//       ...state,
//       products: action.payload
//     };
//   }

//   return state;
// };

// export default productReducer;
import {FETCH_PRODUCTS} from "../actions/productActions";

const initialState = {
  // Khởi tạo state ban đầu của productData
  products: [],
};

const productReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH_PRODUCTS:
      return {
        ...state,
        products: action.payload,
      };
    // Các case khác...
    default:
      return state;
  }
};

export default productReducer;
