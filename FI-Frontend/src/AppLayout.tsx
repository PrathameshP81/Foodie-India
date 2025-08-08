import { Outlet } from "react-router-dom";
import Header from "./Components/HeaderAndFooter/Header";
import Footer from "./Components/HeaderAndFooter/Footer";
import "./css/AppLayout.css";
const AppLayout = () => {
  return (
    <>
      <div className="layout-container">
        <Header />
        <main className="content">
          <Outlet />
        </main>
        <Footer />
      </div>
    </>
  );
};

export default AppLayout;
