import { Link, useNavigate } from "react-router-dom";
import { useGetAllRecepieQuery } from "../Api/RecepieApi";
import { useSelector } from "react-redux";
import type { RootState } from "../store/store";
import { applyFilters } from "../Types/Helper";
import type { RecepieType } from "../Types/Types";
import Filters from "../Components/Helpers/Filters";
import RecepieCard from "../Components/Cards/RecepieCard";
import { useEffect } from "react";

const Recepies = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);
  const navigate = useNavigate();
  const filterState = useSelector((state: RootState) => state.Filterslice);

  if (state.user?.role != "ROLE_ADMIN") {
    navigate("/error");
  }

  const { data, isLoading, refetch } = useGetAllRecepieQuery();

  useEffect(() => {
    refetch();
  }, []);

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
              {applyFilters(filterState, data?.data || []).length > 0 ? (
                <>
                  {applyFilters(filterState, data?.data || []).map(
                    (current: RecepieType) => (
                      <Link
                        to={`/recepies/${current.id}`}
                        style={{ textDecoration: "none" }}
                        key={current.id} // moved key to top-level element
                      >
                        <RecepieCard
                          key={current.id}
                          category={current.category}
                          recepieid={current.id}
                          title={current.title}
                          description={current.description}
                          duration={current.duration}
                          thumbnaiUrl={current.imageUrl}
                          createdByUser={current.createdByUser}
                        />
                      </Link>
                    )
                  )}
                </>
              ) : (
                <>
                  <h1>Recepies Not Found</h1>
                </>
              )}
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default Recepies;
{
  /*  */
}
