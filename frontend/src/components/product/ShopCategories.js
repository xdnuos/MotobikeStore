import PropTypes from "prop-types";
import React from "react";
import {setActiveSort} from "../../helpers/product";
import {multilanguage} from "redux-multilanguage";

const ShopCategories = ({ strings, categories, getSortParams }) => {
  return (
    <div className="sidebar-widget">
      <h4 className="pro-sidebar-title">{strings["categories"]}</h4>
      <div className="sidebar-widget-list mt-30">
        {categories ? (
          <ul>
            <li>
              <div className="sidebar-widget-list-left">
                <button
                  onClick={(e) => {
                    getSortParams("category", "");
                    setActiveSort(e);
                  }}
                >
                  <span className="checkmark" /> {strings["all_categories"]}
                </button>
              </div>
            </li>
            {categories.map((category, key) => {
              return (
                <li key={key}>
                  <div className="sidebar-widget-list-left">
                    <button
                      onClick={(e) => {
                        getSortParams("category", category);
                        setActiveSort(e);
                      }}
                    >
                      {" "}
                      <span className="checkmark" /> {category}{" "}
                    </button>
                  </div>
                </li>
              );
            })}
          </ul>
        ) : (
          "No categories found"
        )}
      </div>
    </div>
  );
};

ShopCategories.propTypes = {
  categories: PropTypes.array,
  getSortParams: PropTypes.func,
};

export default multilanguage(ShopCategories);
