import { useSelector } from "react-redux";
import {
  useGetAllSubscriptionQuery,
  useGetPurchasedSubscriptionQuery,
} from "../Api/SubscriptionApi";
import SubscriptionCard from "../Components/Cards/SubscriptionCard";
import "../css/Subscriptions.css";
import type { RootState } from "../store/store";
import type { SubscriptionType } from "../Types/Types";
import { useNavigate } from "react-router-dom";
const Subscription = () => {
  const { data, isLoading, error } = useGetAllSubscriptionQuery();
  const state = useSelector((state: RootState) => state.AuthSlice);
  const navigate = useNavigate();
  const { data: subscriptions } = useGetPurchasedSubscriptionQuery();

  console.log(subscriptions);

  if (state.user?.role != "ROLE_ADMIN") {
    navigate("/error", { replace: true });
  }

  if (error) {
    return <h1> Something Went Wrong </h1>;
  }

  return (
    <>
      <div className="dashboard-subscription-container">
        <div className="dashboard-subscription-header">
          <h1>Subscriptions</h1>
          <span className="dashboard-subscription-count">
            {data?.data.length} Subscriptions
          </span>
        </div>

        <div className="dashboard-subscription-grid">
          {isLoading ? (
            <>
              <h1> Loading... </h1>
            </>
          ) : (
            <>
              {data?.data != null &&
                data?.data.map((current: SubscriptionType) => {
                  return (
                    <>
                      <SubscriptionCard
                        key={current.subscription_id}
                        subscription_id={current.subscription_id}
                        subscription_name={current.subscription_name}
                        subscription_description={
                          current.subscription_description
                        }
                        subscription_price={current.subscription_price}
                        durationInDays={current.durationInDays}
                        createdByUser={current.createdByUser}
                        createdAt={current.createdAt}
                      />
                    </>
                  );
                })}
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default Subscription;
