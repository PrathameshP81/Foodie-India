import { Outlet, useNavigate } from "react-router-dom";
import Sidebar from "../Components/Admin Dashboard/Sidebar";
import "../css/AdminDashboard.css";
import { useSelector } from "react-redux";
import type { RootState } from "../store/store";
import { useEffect } from "react";

const AdminDashboard = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);
  const navigate = useNavigate();
  if (state.user?.role != "ROLE_ADMIN") {
    navigate("/error");
  }
  useEffect(() => {
    navigate("/dashboard/home");
  }, []);

  return (
    <>
      <div className="container">
        <div className="main-layout">
          {/* <!-- Sidebar Card --> */}
          <Sidebar />

          {/* <!-- Content Card --> */}
          <div id="content-card" className="content-card">
            <Outlet />
          </div>
        </div>
      </div>
    </>
  );
};

export default AdminDashboard;
