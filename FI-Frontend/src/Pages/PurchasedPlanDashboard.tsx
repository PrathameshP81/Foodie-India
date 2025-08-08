import { useNavigate, useParams } from "react-router-dom";
import { useGetPlanByIdQuery } from "../Api/PlanApi";
import "../css/PurchasePlanDashboard.css";
import type { RecepieType } from "../Types/Types";
import { capitalizeSentence } from "../Types/Helper";
import { useSelector } from "react-redux";
import type { RootState } from "../store/store";
import { useEffect, useState } from "react";
import IngredientsCard from "../Components/Cards/IngredientsCard";
import StepsCard from "../Components/Cards/StepsCard";

const PurchasedPlanDashboard = () => {
  const { id } = useParams();
  const { data, error } = useGetPlanByIdQuery(Number(id));
  const state = useSelector((state: RootState) => state.AuthSlice);

  const navigate = useNavigate();
  const isPurchased = state.user?.purchasedPlanIds?.includes(Number(id));

  const [recepie, setRecepie] = useState<RecepieType>(
    data?.data.recepies?.at(0) || []
  );

  if (!isPurchased || state.user?.role != "ROLE_USER") {
    navigate("/error");
  }

  if (error) {
    return (
      <h1> Oops ! Something Went Wrong , Failed to Connect to Server... </h1>
    );
  }

  useEffect(() => {
    if (data?.data.recepies?.length) {
      setRecepie(data.data.recepies[0]);
    }
  }, [data]);

  return (
    <>
      <div className="recipedashboard-dashboard">
        <div className="recipedashboard-column recipedashboard-small-column">
          <h2 className="recipedashboard-header">All Recipes</h2>

          {data?.data.recepies?.map((current: RecepieType) => {
            return (
              <>
                <div
                  className={`recipedashboard-recipe-card ${
                    current.id == recepie.id ? "recipedashboard-selected" : ""
                  } `}
                  onClick={() => {
                    console.log(current.videoUrl);
                    console.log(current);
                    setRecepie(current);
                  }}
                >
                  <img
                    src={current.imageUrl}
                    alt={current.title}
                    className="recipedashboard-recipe-card-image"
                  />
                  <h3 className="recipedashboard-recipe-card-text">
                    {capitalizeSentence(current.title)}
                  </h3>
                </div>
              </>
            );
          })}
        </div>

        <div className="recipedashboard-column recipedashboard-large-column">
          <div className="recipedashboard-recipe-header">
            <h1 className="recipedashboard-recipe-title">
              {capitalizeSentence(data?.data.title || "")} /
              <span style={{ fontSize: "25px" }}> {recepie?.title} </span>
            </h1>
            <span className="recipedashboard-recipe-id">
              Recipe # {recepie?.id}
            </span>
          </div>

          <div className="recipedashboard-content-grid">
            <IngredientsCard
              ingrediants={
                recepie.ingrediants != null
                  ? recepie?.ingrediants!.split("\n")
                  : []
              }
            />
            <StepsCard
              steps={
                recepie.description != null
                  ? recepie?.description!.split("\n")
                  : []
              }
            />
          </div>

          <div className="recipedashboard-video-container">
            <div className="recipedashboard-video-placeholder">
              <video width="100%" height="100%" controls>
                <source
                  src="https://res.cloudinary.com/dwqf0kn9z/video/upload/v1753780755/foodie-india/data/a6ldthsd3llvl6wmhdig.mp4"
                  type="video/mp4"
                />
                Your browser does not support the video tag.
              </video>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default PurchasedPlanDashboard;
