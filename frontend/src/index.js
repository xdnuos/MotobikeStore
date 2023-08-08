import "react-app-polyfill/ie11";
import "react-app-polyfill/stable";
import React from "react";
import ReactDOM from "react-dom";
import {applyMiddleware, createStore} from "redux";
import thunk from "redux-thunk";
import {load, save} from "redux-localstorage-simple";
import {Provider} from "react-redux";
import {fetchProducts} from "./redux/actions/productActions";
import rootReducer from "./redux/reducers/rootReducer";
// import products from "./data/products.json";
import App from "./App";
import "./assets/scss/style.scss";
import * as serviceWorker from "./serviceWorker";
import axios from "axios";

import {composeWithDevTools} from "redux-devtools-extension";

const store = createStore(
  rootReducer,
  load(),
  composeWithDevTools(applyMiddleware(thunk, save()))
);

// fetch products from json file
// store.dispatch(fetchProducts(products));

// Tạo một action creator để gửi yêu cầu API và cập nhật state
const fetchProductsFromAPI = () => {
  return (dispatch) => {
    // Gửi yêu cầu API để lấy dữ liệu
    axios
      .get("https://64b4218e0efb99d86268d1b0.mockapi.io/api/v1/product")
      .then((response) => {
        const products = response.data;
        // Cập nhật state với dữ liệu từ API
        dispatch(fetchProducts(products));
      })
      .catch((error) => {
        // Xử lý lỗi nếu có
        console.error("Error fetching products:", error);
      });
  };
};

// Thay thế dòng dispatch(fetchProducts(products)); với
store.dispatch(fetchProductsFromAPI());

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
