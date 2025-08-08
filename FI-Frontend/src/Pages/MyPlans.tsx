import { Link, useNavigate } from "react-router-dom";
import { useGetPurchasedPlanByUserQuery } from "../Api/PlanApi";
import MyPlanCard from "../Components/Cards/MyPlanCard";
import type { PlanType } from "../Types/Types";
import type { RootState } from "../store/store";
import { useSelector } from "react-redux";

const MyPlans = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);
  const navigate = useNavigate();
  const { data, isLoading, error } = useGetPurchasedPlanByUserQuery();

  if (state.user?.role != "ROLE_USER") {
    navigate("/error");
  }

  if (error) {
    return (
      <h1> Oops ! Something Went Wrong , Failed to Connect to Server... </h1>
    );
  }

  return (
    <>
      <div className="dashboard-plans-container">
        <div className="dashboard-plans-header">
          <h2>Purchased Plans</h2>
          <span className="dashboard-plans-count">
            {data?.data.length || 0} Plans
          </span>
        </div>

        <div className="dashboard-plans-grid">
          {isLoading ? (
            <>
              <h1> Loading... </h1>
            </>
          ) : (
            <>
              {data?.data.map((current: PlanType) => {
                return (
                  <Link
                    to={`/myplans/${current.planid}`}
                    style={{ textDecoration: "none" }}
                  >
                    <MyPlanCard
                      key={current.planid}
                      description={current.description}
                      title={current.title}
                      price={current.price}
                      thumbnaiUrl={current.thumbnaiUrl}
                    />
                  </Link>
                );
              })}
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default MyPlans;
