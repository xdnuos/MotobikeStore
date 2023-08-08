import PropTypes from "prop-types";
import React, {Fragment} from "react";
import MetaTags from "react-meta-tags";
import {Link} from "react-router-dom";
import Tab from "react-bootstrap/Tab";
import Nav from "react-bootstrap/Nav";
import LayoutOne from "../layouts/LayoutOne";
import {multilanguage} from "redux-multilanguage";

const LoginRegister = ({ strings }) => {
  return (
    <Fragment>
      <MetaTags>
        <title>Flone | Login</title>
        <meta
          name="description"
          content="Compare page of flone react minimalist eCommerce template."
        />
      </MetaTags>
      <LayoutOne headerTop="visible">
        {/* breadcrumb */}
        <div className="login-register-area pt-20 pb-100">
          <div className="container">
            <div className="row">
              <div className="col-lg-7 col-md-12 ml-auto mr-auto">
                <div className="login-register-wrapper">
                  <Tab.Container defaultActiveKey="login">
                    <Nav variant="pills" className="login-register-tab-list">
                      <Nav.Item>
                        <Nav.Link eventKey="login">
                          <h4>{strings["login"]}</h4>
                        </Nav.Link>
                      </Nav.Item>
                      <Nav.Item>
                        <Nav.Link eventKey="register">
                          <h4>{strings["register"]}</h4>
                        </Nav.Link>
                      </Nav.Item>
                    </Nav>
                    <Tab.Content>
                      <Tab.Pane eventKey="login">
                        <div className="login-form-container">
                          <div className="login-register-form">
                            <form>
                              <input
                                type="text"
                                name="users-name"
                                placeholder={strings["user_name"]}
                              />
                              <input
                                type="password"
                                name="users-password"
                                placeholder={strings["password"]}
                              />
                              <div className="button-box">
                                <div className="login-toggle-btn">
                                  <input type="checkbox" />
                                  <label className="ml-10">
                                    {strings["remember_me"]}
                                  </label>
                                  <Link to={process.env.PUBLIC_URL + "/"}>
                                    {strings["forgot_pass"]}
                                  </Link>
                                </div>
                                <button type="submit">
                                  <span>{strings["login"]}</span>
                                </button>
                              </div>
                            </form>
                          </div>
                        </div>
                      </Tab.Pane>
                      <Tab.Pane eventKey="register">
                        <div className="login-form-container">
                          <div className="login-register-form">
                            <form>
                              <input
                                type="text"
                                name="users-name"
                                placeholder={strings["user_name"]}
                              />
                              <input
                                type="password"
                                name="users-password"
                                placeholder={strings["password"]}
                              />
                              <input
                                name="users-email"
                                placeholder="Email"
                                type="email"
                              />
                              <div className="button-box">
                                <button type="submit">
                                  <span>{strings["register"]}</span>
                                </button>
                              </div>
                            </form>
                          </div>
                        </div>
                      </Tab.Pane>
                    </Tab.Content>
                  </Tab.Container>
                </div>
              </div>
            </div>
          </div>
        </div>
      </LayoutOne>
    </Fragment>
  );
};

LoginRegister.propTypes = {
  location: PropTypes.object,
};

export default multilanguage(LoginRegister);
