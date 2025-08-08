import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useGetloginUserDetailsMutation } from "../../Api/AuthApi";
import { loggedInUserDetails, loggedOutUser } from "../../Reducers/AuthSlice";
import { jwtDecode } from "jwt-decode";
interface JwtPayload {
  exp: number;
  [key: string]: any;
}

const isTokenValid = (token: string): boolean => {
  if (!token) return false;

  try {
    const decoded: JwtPayload = jwtDecode<JwtPayload>(token);

    const currentTime = Math.floor(Date.now() / 1000); // seconds
    return decoded.exp > currentTime;
  } catch (error) {
    console.error("Invalid token:", error);
    return false;
  }
};

const useAuthCheck = () => {
  const dispatch = useDispatch();
  const [getloginUserDetails] = useGetloginUserDetailsMutation();

  const fetchUserDetails = async (token: string) => {
    try {
      const response = await getloginUserDetails(token);
      const user = response.data?.data;

      if (user) {
        dispatch(
          loggedInUserDetails({
            user: user,
            token: token,
            userId: user?.userId,
          })
        );
      } else {
        dispatch(loggedOutUser());
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("authtoken");

    if (token && isTokenValid(token)) {
      fetchUserDetails(token);
    } else {
      dispatch(loggedOutUser());
    }
  }, [dispatch]);
};

export default useAuthCheck;
