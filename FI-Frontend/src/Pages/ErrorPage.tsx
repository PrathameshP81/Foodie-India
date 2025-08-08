import { useNavigate } from "react-router-dom";
import errorpage from "../assets/errorpage (2).png";
import "../css/ErrorPage.css";

const ErrorPage = () => {
  const navigate = useNavigate();
  return (
    <>
      <div className="ErrorContainer">
        <img
          src={errorpage}
          style={{ margin: "auto", display: "block" }}
          alt="404 Not Found"
        />
        <button
          className="error-btn-outline"
          onClick={() => {
            navigate("/", { replace: true });
          }}
        >
          Go To Home Page
        </button>
      </div>
    </>
  );
};

export default ErrorPage;
