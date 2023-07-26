// export const FETCH_PRODUCTS_SUCCESS = "FETCH_PRODUCTS_SUCCESS";

// const fetchProductsSuccess = products => ({
//   type: FETCH_PRODUCTS_SUCCESS,
//   payload: products
// });

// // fetch products
// export const fetchProducts = products => {
//   return dispatch => {
//     dispatch(fetchProductsSuccess(products));
//   };
// };
export const FETCH_PRODUCTS = "FETCH_PRODUCTS";

// Action creator để gửi dữ liệu lấy từ API đến reducer
export const fetchProducts = (products) => ({
  type: FETCH_PRODUCTS,
  payload: products,
});
