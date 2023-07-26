import currencyReducer from "./currencyReducer";
import productReducer from "./productReducer";
import cartReducer from "./cartReducer";
import compareReducer from "./compareReducer";
import { combineReducers } from "redux";
import { createMultilanguageReducer } from "redux-multilanguage";

const rootReducer = combineReducers({
  multilanguage: createMultilanguageReducer({ currentLanguageCode: "vi" }),
  currencyData: currencyReducer,
  productData: productReducer,
  cartData: cartReducer,
  compareData: compareReducer,
});

export default rootReducer;
