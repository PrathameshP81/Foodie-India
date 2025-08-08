import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { loggedOutUser } from "../Reducers/AuthSlice";
import { useDispatch } from "react-redux";

const Logout = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(loggedOutUser());
    navigate("/login", { replace: true });
  }, [navigate]);

  return null;
};

export default Logout;
