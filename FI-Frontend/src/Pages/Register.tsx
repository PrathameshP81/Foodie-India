import { useSelector } from "react-redux";
import RegisterForm from "../Components/Forms/RegisterForm";
import type { RootState } from "../store/store";
import ErrorPage from "./ErrorPage";

const Register = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);

  if (!state.isLoggedIn) {
    return (
      <>
        <RegisterForm />
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

export default Register;
