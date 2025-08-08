import { useNavigate, useParams } from "react-router-dom";
import StepsCard from "./StepsCard";
import IngredientsCard from "./IngredientsCard";
import { useGetRecepieByIdQuery } from "../../Api/RecepieApi";
import type { RootState } from "../../store/store";
import { useSelector } from "react-redux";
import "../../css/PlanDetails.css";

const RecepieDetails = () => {
  const { id } = useParams();
  const state = useSelector((state: RootState) => state.AuthSlice);
  const navigate = useNavigate();

  if (state.user?.role != "ROLE_ADMIN") {
    navigate("/error", { replace: true });
  }

  const { data, isLoading, error } = useGetRecepieByIdQuery(Number(id));
  const recepie = data?.data!;
  console.log(recepie);
  // const IngrdientsArray = recepie?.ingrediants?.split("\n") || [];
  // const StepsArray = recepie?.description.split("\n") || [];

  if (error) {
    return (
      <h1> Oops ! Something Went Wrong , Failed to Connect to Server... </h1>
    );
  }

  return (
    <>
      <div className="single-plan-card">
        {isLoading ? (
          <>
            <h1> Loading... </h1>
          </>
        ) : (
          <>
            <div className="single-image-column">
              <img src={recepie?.imageUrl} alt={recepie?.title} />
            </div>

            <div className="single-info-column">
              <IngredientsCard
                ingrediants={
                  data?.data.ingrediants != null
                    ? data?.data?.ingrediants!.split("\n")
                    : []
                }
              />
              <StepsCard
                steps={
                  data?.data.description != null
                    ? data?.data?.description!.split("\n")
                    : []
                }
              />
            </div>
          </>
        )}
      </div>
    </>
  );
};

export default RecepieDetails;
