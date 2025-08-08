import { useForm, type FieldValues } from "react-hook-form";
import "../../css/RegisterForm.css";
import logo from "../../assets/logo.png";

import toast from "react-hot-toast";
import { useRegisterUserMutation } from "../../Api/AuthApi";
import type { ApiResponse, UserType } from "../../Types/Types";

const RegisterForm = () => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const [registerUser, { isLoading }] = useRegisterUserMutation();

  const onSubmit = async (data: FieldValues) => {
    try {
      data["role"] = data["isAdmin"] ? "ROLE_ADMIN" : "ROLE_USER";

      const response: ApiResponse<UserType> = await registerUser(data).unwrap();

      if (response.status === 200) {
        toast.success(response.message);
        reset();
      } else {
        toast.error(response.message);
      }
    } catch (error: any) {
      console.log(error);
      toast.error(error?.data?.message || "Something Went Wrong...");
    }
  };

  return (
    // <div className="two-column-container">
    //   {/* Left column - Image */}
    //   <div className="image-column">
    //     <img src={logo} alt="Register" />
    //   </div>

    //   {/* Right column - Form */}
    //   <div className="form-column">
    //     <h2 className="card-title">Register</h2>
    //     <form onSubmit={handleSubmit(onSubmit)} className="form-flex">
    //       <div className="form-group">
    //         <label>Name</label>
    //         <input
    //           type="text"
    //           {...register("name", { required: "Name is required" })}
    //         />
    //         {errors.name && (
    //           <p className="error">{String(errors.name.message)}</p>
    //         )}
    //       </div>

    //       <div className="form-group">
    //         <label>Email</label>
    //         <input
    //           type="email"
    //           {...register("email", {
    //             required: "Email is required",
    //             pattern: {
    //               value: /^\S+@\S+$/i,
    //               message: "Invalid email address",
    //             },
    //           })}
    //         />
    //         {errors.email && (
    //           <p className="error">{String(errors.email.message)}</p>
    //         )}
    //       </div>

    //       <div className="form-group">
    //         <label>Password</label>
    //         <input
    //           type="password"
    //           {...register("password", {
    //             required: "Password is required",
    //             minLength: {
    //               value: 8,
    //               message: "Password must be at least 8 characters",
    //             },
    //           })}
    //         />
    //         {errors.password && (
    //           <p className="error">{String(errors.password.message)}</p>
    //         )}
    //       </div>

    //       <div className="form-group">
    //         <label>Gender</label>
    //         <select {...register("gender", { required: "Gender is required" })}>
    //           <option value="">Select</option>
    //           <option value="Female">Female</option>
    //           <option value="Male">Male</option>
    //           <option value="Other">Other</option>
    //         </select>
    //         {errors.gender && (
    //           <p className="error">{String(errors.gender.message)}</p>
    //         )}
    //       </div>

    //       <div className="form-group">
    //         <label>Phone</label>
    //         <input
    //           type="tel"
    //           {...register("phone", {
    //             required: "Phone is required",
    //             pattern: {
    //               value: /^[6-9]\d{9}$/,
    //               message: "Invalid Indian phone number",
    //             },
    //           })}
    //         />
    //         {errors.phone && (
    //           <p className="error">{String(errors.phone.message)}</p>
    //         )}
    //       </div>

    //       <div className="form-group">
    //         <label className="checkbox-group">
    //           <input type="checkbox" {...register("isAdmin")} />
    //           Register as Admin?
    //         </label>
    //       </div>

    //       <button type="submit" className="submit-btn" disabled={isLoading}>
    //         {isLoading ? "Registering..." : "Register"}
    //       </button>
    //     </form>
    //   </div>
    // </div>

    <div className="two-column-container">
      {/* Left column - Image */}
      <div className="image-column">
        <img src={logo} alt="Register" />
      </div>

      {/* Right column - Form */}
      <div className="form-column">
        <h2 className="card-title">Register</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="form-flex">
          <div className="form-group">
            <label>Name</label>
            <input
              type="text"
              {...register("name", { required: "Name is required" })}
            />
            {errors.name && (
              <p className="error">{String(errors.name.message)}</p>
            )}
          </div>

          <div className="form-group">
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
            />
            {errors.email && (
              <p className="error">{String(errors.email.message)}</p>
            )}
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              {...register("password", {
                required: "Password is required",
                minLength: {
                  value: 8,
                  message: "Password must be at least 8 characters",
                },
              })}
            />
            {errors.password && (
              <p className="error">{String(errors.password.message)}</p>
            )}
          </div>

          <div className="form-group">
            <label>Gender</label>
            <select {...register("gender", { required: "Gender is required" })}>
              <option value="">Select</option>
              <option value="Female">Female</option>
              <option value="Male">Male</option>
              <option value="Other">Other</option>
            </select>
            {errors.gender && (
              <p className="error">{String(errors.gender.message)}</p>
            )}
          </div>

          <div className="form-group">
            <label>Phone</label>
            <input
              type="tel"
              {...register("phone", {
                required: "Phone is required",
                pattern: {
                  value: /^[6-9]\d{9}$/,
                  message: "Invalid Indian phone number",
                },
              })}
            />
            {errors.phone && (
              <p className="error">{String(errors.phone.message)}</p>
            )}
          </div>

          <div className="form-group full-width">
            <label className="checkbox-group">
              <input type="checkbox" {...register("isAdmin")} />
              Register as Admin?
            </label>
          </div>

          <div className="form-group full-width">
            <button type="submit" className="submit-btn" disabled={isLoading}>
              {isLoading ? "Registering..." : "Register"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RegisterForm;
