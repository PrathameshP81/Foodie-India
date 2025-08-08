import { useForm, type FieldValues } from "react-hook-form";
import logo from "../../assets/logo.png";
import "../../css/LoginForm.css";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import {
  useGetloginUserDetailsMutation,
  useLoginUserMutation,
} from "../../Api/AuthApi";
import type {
  ApiResponse,
  LoginResponseType,
  UserType,
} from "../../Types/Types";
import {
  loggedInUser,
  loggedInUserDetails,
  loggedOutUser,
} from "../../Reducers/AuthSlice";

const LoginForm = () => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const [loginUser, { isLoading }] = useLoginUserMutation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [getloginUserDetails] = useGetloginUserDetailsMutation();

  const onSubmit = async (data: FieldValues) => {
    try {
      const response: ApiResponse<LoginResponseType> = await loginUser(
        data
      ).unwrap();

      if (response.status == 200) {
        toast.success(response.message);

        // Setting userId and Token

        dispatch(
          loggedInUser({
            userId: response.data.userId,
            token: response.data.token,
          })
        );

        reset();

        // Fetching and Setting Logged in User Details

        const res: ApiResponse<UserType> = await getloginUserDetails(
          response.data.token
        ).unwrap();

        const user = res.data;

        if (res.status == 200) {
          dispatch(loggedInUserDetails({ userId: user.userId, user: user }));
          navigate("/", { replace: true });
        } else {
          dispatch(loggedOutUser());
        }
      }
    } catch (error: any) {
      console.log(error);
      toast.error(error?.data?.message || "Something Went Wrong... ");
    }
  };

  return (
    <>
      <div className="login-container">
        {/* Left column - Image */}
        <div className="login-image-section">
          <img src={logo} alt="Login" />
        </div>

        {/* Right column - Form */}
        <div className="login-form-section">
          <h2 className="login-title">Login</h2>
          <form onSubmit={handleSubmit(onSubmit)} className="login-form">
            {/* Email */}
            <div className="login-group">
              <label>Email</label>
              <input
                type="email"
                {...register("email", {
                  required: "Email is required",
                  pattern: {
                    value: /^\S+@\S+$/i,
                    message: "Invalid email address",
                  },
                })}
                placeholder="you@example.com"
              />
              {errors.email && (
                <p className="login-error">{String(errors.email.message)}</p>
              )}
            </div>

            {/* Password */}
            <div className="login-group">
              <label>Password</label>
              <input
                type="password"
                {...register("password", {
                  required: "Password is required",
                  minLength: {
                    value: 6,
                    message: "Password must be at least 6 characters",
                  },
                })}
                placeholder="Enter your password"
              />
              {errors.password && (
                <p className="login-error">{String(errors.password.message)}</p>
              )}
            </div>

            <button type="submit" className="login-btn" disabled={isLoading}>
              {isLoading ? "Logging in.." : "Login"}
            </button>
          </form>
        </div>
      </div>
    </>
  );
};

export default LoginForm;
