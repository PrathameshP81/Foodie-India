import { NavLink, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import type { RootState } from "../../store/store";
import { loggedOutUser } from "../../Reducers/AuthSlice";

const Header = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  return (
    <>
      <header className="navbar">
        <div className="nav-content">
          <div className="logo-section">
            <p> Foodie India </p>
          </div>

          <nav className="nav-links">
            <NavLink to="/" className="navlink">
              Home
            </NavLink>

            {state.isLoggedIn ? (
              <>
                <NavLink to="/plans" className="navlink">
                  Plans
                </NavLink>

                {state.user?.role == "ROLE_ADMIN" ? (
                  <>
                    <NavLink to="/recepies" className="navlink">
                      Recepies
                    </NavLink>
                    <NavLink to="/dashboard" className="navlink">
                      Admin Dashboard
                    </NavLink>
                    <NavLink to="/subscription" className="navlink">
                      Subscriptions
                    </NavLink>
                  </>
                ) : (
                  <>
                    <NavLink to="/myplans" className="navlink">
                      My Plans
                    </NavLink>
                  </>
                )}

                <li
                  className="navlink"
                  style={{ listStyle: "none", cursor: "pointer" }}
                  onClick={() => {
                    dispatch(loggedOutUser());
                    navigate("/");
                  }}
                >
                  Logout
                </li>

                <div className="profile-section">
                  <p
                    className="profile_letter"
                    style={{ color: "white", fontSize: "20px" }}
                  >
                    {state.user?.name[0].toUpperCase()}
                  </p>
                </div>
              </>
            ) : (
              <>
                <NavLink to="/register" className="navlink">
                  Register
                </NavLink>
                <NavLink to="/login" className="navlink">
                  Login
                </NavLink>
              </>
            )}
          </nav>
        </div>
      </header>
    </>
  );
};

export default Header;
