import { useForm, type FieldValues } from "react-hook-form";
import toast from "react-hot-toast";
import type { RootState } from "../../../store/store";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { setUpdatedPlanStatus } from "../../../Reducers/SetUpdatedDataSlice";
import {
  useCreatePlanMutation,
  useUpdatePlanMutation,
} from "../../../Api/PlanApi";
import type { RecepieType } from "../../../Types/Types";

const AdminPlanForm = ({
  Recepieids,
  setRecepie,
  setRecepieids,
}: {
  Recepieids: number[];
  setRecepie: (recepie: RecepieType[]) => void;
  setRecepieids: (ids: number[]) => void;
}) => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FieldValues>();

  const [createPlan, { isLoading: createLoading }] = useCreatePlanMutation();
  const [updatePlan, { isLoading: updateLoading }] = useUpdatePlanMutation();

  const state = useSelector((state: RootState) => state.setUpdatedDataSlice);
  const dispatch = useDispatch();

  // Form Submission
  const onSubmit = async (data: FieldValues) => {
    const formData = new FormData();

    const planData = {
      title: data.title,
      description: data.description,
      price: data.price,
      category: data.category,
      recepieIds: Recepieids,
    };

    if (!state.updatedPlanStatus) {
      if (data.image?.[0]) {
        formData.append("file", data.image[0]);
      }

      const jsonBlob = new Blob([JSON.stringify(planData)], {
        type: "application/json",
      });

      formData.append("data", jsonBlob);

      try {
        const response = await createPlan(formData).unwrap();
        toast.success(response.message);
        reset();
        setRecepie([]);
        setRecepieids([]);
      } catch (error: any) {
        toast.error(error?.data?.message || "Something Went Wrong...");
      }
    } else {
      const updatedFormData = new FormData();

      if (data.image?.[0]) {
        updatedFormData.append("file", data.image[0]);
      }

      // Append planId to FormData (needed in your backend endpoint)
      updatedFormData.append("planid", String(state.updatedPlanData.planid));

      const updateJsonBlob = new Blob([JSON.stringify(planData)], {
        type: "application/json",
      });

      updatedFormData.append("data", updateJsonBlob);

      try {
        const response = await updatePlan(updatedFormData).unwrap();
        toast.success(response.message);
        reset();
        setRecepie([]);
        setRecepieids([]);
        dispatch(setUpdatedPlanStatus());
      } catch (error: any) {
        toast.error(error?.data?.message || "Update Failed");
      }
    }
  };

  useEffect(() => {
    if (state.updatedPlanStatus) {
      reset({
        title: state.updatedPlanData.title,
        category: state.updatedPlanData.category,
        description: state.updatedPlanData.description,
        price: state.updatedPlanData.price,
      });

      setRecepie(state.updatedPlanData.recepies);
      setRecepieids(state.updatedPlanData.recepies.map((r) => r.id));
    } else {
      reset({
        title: "",
        category: "",
        description: "",
        price: 0,
      });
      setRecepie([]);
      setRecepieids([]);
    }
  }, [state.updatedPlanStatus]);

  return (
    <>
      <form className="rd-form" onSubmit={handleSubmit(onSubmit)}>
        <div className="rd-form-group">
          <label htmlFor="title">Plan Title</label>
          <input
            type="text"
            id="title"
            {...register("title", { required: "Plan Title is required" })}
            placeholder="Enter Plan Title"
          />
          {errors.title && (
            <p className="text-red-500 rd-error text-sm">
              {String(errors.title.message)}
            </p>
          )}
        </div>

        <div className="rd-form-group">
          <label htmlFor="category">Category</label>
          <select
            id="category"
            {...register("category", {
              required: "Category is required",
            })}
          >
            <option value="">Select category</option>
            <option value="VEG">VEG</option>
            <option value="NONVEG">NONVEG</option>
          </select>
          {errors.category && (
            <p className="text-red-500 rd-error text-sm">
              {String(errors.category.message)}
            </p>
          )}
        </div>

        <div className="rd-form-group">
          <label htmlFor="description">Description</label>
          <textarea
            rows={4}
            id="description"
            {...register("description", {
              required: "Description is required",
            })}
            placeholder="Describe the Plan..."
          ></textarea>
          {errors.description && (
            <p className="text-red-500 rd-error text-sm">
              {String(errors.description.message)}
            </p>
          )}
        </div>

        <div className="rd-row">
          <div className="rd-form-group">
            <label htmlFor="price">Plan Price</label>
            <input
              type="number"
              id="price"
              {...register("price", {
                required: "Plan Price is required",
              })}
              placeholder="Enter Plan Price"
            />
            {errors.price && (
              <p className="text-red-500 rd-error text-sm">
                {String(errors.price.message)}
              </p>
            )}
          </div>

          <div className="rd-form-group">
            <label htmlFor="image">Recipe Image</label>
            <input
              type="file"
              id="image"
              accept="image/*"
              {...register("image", {
                required: !state.updatedPlanStatus && "Image is required",
              })}
            />
            {errors.image && (
              <p className="text-red-500 rd-error text-sm">
                {String(errors.image.message)}
              </p>
            )}
          </div>
        </div>

        <div style={{ display: "flex", gap: "1rem" }}>
          {state.updatedPlanStatus ? (
            <>
              <button type="submit" className="rd-btn">
                {updateLoading ? "Updating Plan..." : "Update Plan"}
              </button>

              <button
                type="button"
                className="rd-btn"
                onClick={() => {
                  dispatch(setUpdatedPlanStatus());
                  reset();
                  setRecepie([]);
                  setRecepieids([]);
                }}
              >
                Cancel
              </button>
            </>
          ) : (
            <>
              <button type="submit" className="rd-btn">
                {createLoading ? "Creating Plan..." : "Create Plan"}
              </button>
            </>
          )}
        </div>
      </form>
    </>
  );
};

export default AdminPlanForm;
