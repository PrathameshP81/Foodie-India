import { NavLink } from "react-router-dom";

const Sidebar = () => {
  return (
    <div className="sidebar">
      <div className="sidebar-content">
        <h1 className="sidebar-title">
          <svg
            className="dashboard-icon"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M4 6a2 2 0 012-2h12a2 2 0 012 2v12a2 2 0 01-2 2H6a2 2 0 01-2-2V6z"
            />
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M9 3v18m6-18v18"
            />
          </svg>
          Dashboard
        </h1>

        <div className="nav-section">
          <h2 className="nav-section-title">Main</h2>
          <ul id="nav-links">
            <li className="sidebar-item">
              <NavLink
                to="/dashboard/home"
                className={({ isActive }) =>
                  `sidebar-link ${isActive ? "active" : ""}`
                }
                style={{ listStyle: "none" }}
              >
                {/* Home Icon */}
                <svg
                  className="dashboard-icon"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z"
                  />
                </svg>
                Home
              </NavLink>
            </li>

            <li className="sidebar-item">
              <NavLink
                to="/dashboard/plan"
                className={({ isActive }) =>
                  `sidebar-link ${isActive ? "active" : ""}`
                }
                style={{ listStyle: "none" }}
              >
                {/* Plans Icon */}
                <svg
                  className="dashboard-icon"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
                  />
                </svg>
                Plans
              </NavLink>
            </li>

            <li className="sidebar-item">
              <NavLink
                to="/dashboard/recepie"
                className={({ isActive }) =>
                  `sidebar-link ${isActive ? "active" : ""}`
                }
                style={{ listStyle: "none" }}
              >
                {/* Recepie Icon */}
                <svg
                  className="dashboard-icon"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"
                  />
                </svg>
                Recepie
              </NavLink>
            </li>

            <li className="sidebar-item">
              <NavLink
                to="/dashboard/subscription"
                className={({ isActive }) =>
                  `sidebar-link ${isActive ? "active" : ""}`
                }
                style={{ listStyle: "none" }}
              >
                {/* Subscription Icon */}
                <svg
                  className="dashboard-icon"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"
                  />
                </svg>
                Subscription
              </NavLink>
            </li>
          </ul>
        </div>

        <div className="nav-section">
          <h2 className="nav-section-title">Settings</h2>
          <ul>
            <li className="sidebar-item">
              <NavLink
                to="/logout"
                className={({ isActive }) =>
                  `sidebar-link ${isActive ? "active" : ""}`
                }
                style={{ listStyle: "none" }}
              >
                {/* Logout Icon */}
                <svg
                  className="dashboard-icon"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"
                  />
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                </svg>
                Logout
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
