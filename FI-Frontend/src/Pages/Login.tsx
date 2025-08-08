import { useSelector } from "react-redux";
import type { RootState } from "../store/store";
import ErrorPage from "./ErrorPage";
import LoginForm from "../Components/Forms/LoginForm";

const Login = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);

  if (!state.isLoggedIn) {
    return (
      <>
        <LoginForm />
      </>
    );
  } else {
    return (
      <>
        <ErrorPage />
      </>
    );
  }
};

export default Login;
