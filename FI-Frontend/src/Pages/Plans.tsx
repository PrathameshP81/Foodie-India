import { useSelector } from "react-redux";
import ErrorPage from "./ErrorPage";
import type { RootState } from "../store/store";
import { useGetAllPlansQuery } from "../Api/PlanApi";
import type { PlanType } from "../Types/Types";
import { Link } from "react-router-dom";
import { applyFilters } from "../Types/Helper";
import Filters from "../Components/Helpers/Filters";
import PlanCard from "../Components/Cards/PlanCard";
import { useEffect } from "react";

const Plans = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);
  const filterState = useSelector((state: RootState) => state.Filterslice);

  const { data: plans, isLoading, refetch } = useGetAllPlansQuery();

  useEffect(() => {
    refetch();
  }, []);

  if (state.isLoggedIn) {
    return (
      <>
        <div className="plate-container">
          {/* Filter Card */}

          <Filters />

          <div className="plate-plans-container">
            {isLoading ? (
              <h1>Loading...</h1>
            ) : (
              <>
                {applyFilters(filterState, plans?.data || []).length > 0 ? (
                  <>
                    {applyFilters(filterState, plans?.data || []).map(
                      (current: PlanType) => (
                        <Link
                          to={`/plans/${current.planid}`}
                          style={{ textDecoration: "none" }}
                        >
                          <PlanCard
                            price={current.price}
                            category={current.category}
                            planid={current.planid}
                            title={current.title}
                            description={current.description}
                            thumbnaiUrl={current.thumbnaiUrl}
                            createdAt={current.createdAt}
                            createdByUser={current.createdByUser}
                            key={current.planid}
                          />
                        </Link>
                      )
                    )}
                  </>
                ) : (
                  <>
                    <h1>Plan Not Found</h1>
                  </>
                )}
              </>
            )}
          </div>
        </div>
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

export default Plans;
