import PropTypes from "prop-types";
import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import { useToasts } from "react-toast-notifications";
import { getDiscountPrice } from "../../../helpers/product";
import { multilanguage } from "redux-multilanguage";

const MenuCart = ({ strings, cartData, currency, deleteFromCart }) => {
  let cartTotalPrice = 0;
  const { addToast } = useToasts();
  return (
    <div className="shopping-cart-content">
      {cartData && cartData.length > 0 ? (
        <Fragment>
          <ul>
            {cartData.map((single, key) => {
              const discountedPrice = getDiscountPrice(
                single.price,
                single.discount
              );
              const finalProductPrice = (
                single.price * currency.currencyRate
              ).toFixed(0);
              const finalDiscountedPrice = (
                discountedPrice * currency.currencyRate
              ).toFixed(0);

              discountedPrice != null
                ? (cartTotalPrice += finalDiscountedPrice * single.quantity)
                : (cartTotalPrice += finalProductPrice * single.quantity);

              return (
                <li className="single-shopping-cart" key={key}>
                  <div className="shopping-cart-img">
                    <Link to={process.env.PUBLIC_URL + "/product/" + single.id}>
                      <img
                        alt=""
                        src={process.env.PUBLIC_URL + single.image[0]}
                        className="img-fluid"
                      />
                    </Link>
                  </div>
                  <div className="shopping-cart-title">
                    <h4>
                      <Link
                        to={process.env.PUBLIC_URL + "/product/" + single.id}
                      >
                        {" "}
                        {single.name}{" "}
                      </Link>
                    </h4>
                    <h6>
                      {strings["qty"]}: {single.quantity}
                    </h6>
                    <span>
                      {discountedPrice !== null
                        ? finalDiscountedPrice + " " + currency.currencySymbol
                        : finalProductPrice + " " + currency.currencySymbol}
                    </span>
                    {single.selectedProductColor &&
                    single.selectedProductSize ? (
                      <div className="cart-item-variation">
                        <span>
                          {strings["color"]}: {single.selectedProductColor}
                        </span>
                        <span>
                          {strings["size"]}: {single.selectedProductSize}
                        </span>
                      </div>
                    ) : (
                      ""
                    )}
                  </div>
                  <div className="shopping-cart-delete">
                    <button onClick={() => deleteFromCart(single, addToast)}>
                      <i className="fa fa-times-circle" />
                    </button>
                  </div>
                </li>
              );
            })}
          </ul>
          <div className="shopping-cart-total">
            <h4>
              {strings["total"]} :{" "}
              <span className="shop-total">
                {cartTotalPrice.toFixed(0) + " " + currency.currencySymbol}
              </span>
            </h4>
          </div>
          <div className="shopping-cart-btn btn-hover text-center">
            <Link className="default-btn" to={process.env.PUBLIC_URL + "/cart"}>
              {strings["cart"]}
            </Link>
            <Link
              className="default-btn"
              to={process.env.PUBLIC_URL + "/checkout"}
            >
              {strings["checkout"]}
            </Link>
          </div>
        </Fragment>
      ) : (
        <p className="text-center">{strings["no_item_in_cart"]}</p>
      )}
    </div>
  );
};

MenuCart.propTypes = {
  cartData: PropTypes.array,
  currency: PropTypes.object,
  deleteFromCart: PropTypes.func,
};

export default multilanguage(MenuCart);
