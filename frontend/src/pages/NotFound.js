import PropTypes from "prop-types";
import React, {Fragment} from "react";
import {Link} from "react-router-dom";
import MetaTags from "react-meta-tags";
import LayoutOne from "../layouts/LayoutOne";
import {multilanguage} from "redux-multilanguage";

const NotFound = ({ strings, location }) => {
  return (
    <Fragment>
      <MetaTags>
        <title>Flone | Not Found</title>
        <meta
          name="description"
          content="404 page of flone react minimalist eCommerce template."
        />
      </MetaTags>
      <LayoutOne headerTop="visible">
        {/* breadcrumb */}
        <div className="error-area pt-20 pb-100">
          <div className="container">
            <div className="row justify-content-center">
              <div className="col-xl-7 col-lg-8 text-center">
                <div className="error">
                  <h1>404</h1>
                  <h2>{strings["page_not_found"]}</h2>
                  <p>{strings["page_not_found_desc"]}</p>
                  <form className="searchform mb-50">
                    <input
                      type="text"
                      name="search"
                      id="error_search"
                      placeholder="Search..."
                      className="searchform__input"
                    />
                    <button type="submit" className="searchform__submit">
                      <i className="fa fa-search" />
                    </button>
                  </form>
                  <Link to={process.env.PUBLIC_URL + "/"} className="error-btn">
                    {strings["back_to_home"]}
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </LayoutOne>
    </Fragment>
  );
};

NotFound.propTypes = {
  location: PropTypes.object,
};

export default multilanguage(NotFound);
