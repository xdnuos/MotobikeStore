import PropTypes from "prop-types";
import React from "react";
import { setActiveLayout } from "../../helpers/product";
import { multilanguage } from "redux-multilanguage";

const ShopTopAction = ({
  strings,
  getLayout,
  getFilterSortParams,
  productCount,
  sortedProductCount,
}) => {
  return (
    <div className="shop-top-bar mb-35">
      <div className="select-shoing-wrap">
        <div className="shop-select">
          <select
            onChange={(e) => getFilterSortParams("filterSort", e.target.value)}
          >
            <option value="default">{strings["default"]}</option>
            <option value="priceHighToLow">{strings["price_H2L"]}</option>
            <option value="priceLowToHigh">{strings["price_L2H"]}</option>
          </select>
        </div>
        <p>
          {strings["showing"]} {sortedProductCount}
          {strings["of"]} {productCount} {strings["result"]}
        </p>
      </div>

      <div className="shop-tab">
        <button
          onClick={(e) => {
            getLayout("grid two-column");
            setActiveLayout(e);
          }}
        >
          <i className="fa fa-th-large" />
        </button>
        <button
          onClick={(e) => {
            getLayout("grid three-column");
            setActiveLayout(e);
          }}
        >
          <i className="fa fa-th" />
        </button>
        <button
          onClick={(e) => {
            getLayout("list");
            setActiveLayout(e);
          }}
        >
          <i className="fa fa-list-ul" />
        </button>
      </div>
    </div>
  );
};

ShopTopAction.propTypes = {
  getFilterSortParams: PropTypes.func,
  getLayout: PropTypes.func,
  productCount: PropTypes.number,
  sortedProductCount: PropTypes.number,
};

export default multilanguage(ShopTopAction);
