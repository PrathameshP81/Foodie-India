import { useState } from "react";
import { useForm, type FieldValues } from "react-hook-form";
import { useCreateRecepieMutation } from "../../../Api/RecepieApi";
import toast from "react-hot-toast";

export default function AdminRecepieForm({
  setIngrediants,
  setSteps,
  ingrediants,
  steps,
}: {
  setIngrediants: (val: string[]) => void;
  setSteps: (val: string[]) => void;
  ingrediants: string[];
  steps: string[];
}) {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
    reset,
  } = useForm();

  const [Ingrediantvalue, setIngrediantValue] = useState("");
  const [Stepvalue, stepStepValue] = useState("");
  const [createRecepie] = useCreateRecepieMutation();

  const onSubmit = async (data: FieldValues) => {
    const formData = new FormData();

    const recepieData = {
      title: data.title,
      category: data.category,
      recepiedescription: steps.join("\n"),
      ingrediants: ingrediants.join("\n"),
    };

    if (data.image?.[0] && data.video?.[0]) {
      formData.append("file", data.image[0]);
      formData.append("file", data.video[0]);
    }

    const jsonBlob = new Blob([JSON.stringify(recepieData)], {
      type: "application/json",
    });

    formData.append("data", jsonBlob);

    try {
      const response = await createRecepie(formData).unwrap();
      toast.success(response.message);
      setIngrediants([]);
      setSteps([]);
      reset();
    } catch (error: any) {
      console.error("Error submitting recipe:", error);
      toast.error(error.data.message || "Something Went Wrong....");
    }
  };

  return (
    <form className="rd-form" onSubmit={handleSubmit(onSubmit)}>
      <div className="rd-row">
        <div className="rd-form-group">
          <label htmlFor="title">Title</label>
          <input
            id="title"
            type="text"
            {...register("title", { required: "Title is required" })}
            placeholder="Enter title"
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
            style={{ width: "220px" }}
            id="category"
            {...register("category", { required: "Category is required" })}
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
      </div>

      <div className="rd-form-group">
        <label htmlFor="ingrediants">Ingredients</label>
        <div className="input-group">
          <input
            type="text"
            name="incrediants"
            value={Ingrediantvalue}
            onChange={(e) => {
              setIngrediantValue(e.target.value);
            }}
            placeholder="Add an ingredient..."
          />
          <button
            id="addButton"
            type="button"
            onClick={() => {
              if (Ingrediantvalue.trim().length > 5) {
                setIngrediants([...ingrediants, Ingrediantvalue]);
                setIngrediantValue("");
              }
            }}
          >
            Add Ingredient
          </button>
        </div>
        {errors.ingrediants && (
          <p className="text-red-500 rd-error text-sm">
            {String(errors.ingrediants.message)}
          </p>
        )}
      </div>
      <div className="rd-form-group">
        <label htmlFor="recepiedescription">Recipe Description</label>
        <div className="input-group">
          <input
            type="text"
            name="steps"
            value={Stepvalue}
            placeholder="Add the steps..."
            onChange={(e) => {
              stepStepValue(e.target.value);
            }}
          />
          <button
            id="addButton"
            type="button"
            onClick={() => {
              if (Stepvalue.trim().length > 5) {
                setSteps([...steps, Stepvalue]);
                stepStepValue("");
              }
            }}
          >
            Add Steps
          </button>
        </div>
        {errors.recepiedescription && (
          <p className="text-red-500 rd-error text-sm">
            {String(errors.recepiedescription.message)}
          </p>
        )}
      </div>
      <div className="rd-row">
        <div className="rd-form-group">
          <label htmlFor="image">Upload Image</label>
          <input
            id="image"
            type="file"
            accept="image/*"
            {...register("image", { required: "Image is required" })}
          />
          {errors.image && (
            <p className="text-red-500 rd-error text-sm">
              {String(errors.image.message)}
            </p>
          )}
        </div>

        <div className="rd-form-group">
          <label htmlFor="video">Upload Video</label>
          <input
            id="video"
            type="file"
            accept="video/*"
            {...register("video", { required: "Video is required" })}
          />
          {errors.video && (
            <p className="text-red-500 rd-error text-sm">
              {String(errors.video.message)}
            </p>
          )}
        </div>
      </div>

      <div style={{ marginTop: "1rem" }}>
        <button type="submit" className="rd-btn" disabled={isSubmitting}>
          {isSubmitting ? "Creating Recepie..." : "Create Recipe"}
        </button>
      </div>
    </form>
  );
}
