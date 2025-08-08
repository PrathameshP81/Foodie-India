import { useGetPurchasedSubscriptionQuery } from "../../Api/SubscriptionApi";
import "../../css/AdminSubscription.css";
import { formatDate } from "../../Types/Helper";
import type { SubscriptionPurchaseType } from "../../Types/Types";
const AdminSubscription = () => {
  const { data, isLoading } = useGetPurchasedSubscriptionQuery();
  const subscriptions = data?.data;

  return (
    <>
      <div data-page="AdminSubscription" className="page-content">
        <div className="content-section">
          <div className="page-header">
            <h2 className="page-title">Admin Subscription's History</h2>
          </div>

          <div className="table-container">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Subscription Id</th>
                  <th>Purchased At</th>
                  <th>Start Time</th>
                  <th>End Time</th>
                  <th>Activation Status</th>
                </tr>
              </thead>

              <tbody>
                {isLoading ? (
                  <>
                    <h1> Loading </h1>
                  </>
                ) : (
                  <>
                    {subscriptions?.map((current: SubscriptionPurchaseType) => {
                      return (
                        <>
                          <tr key={current.subscription_id}>
                            <td>
                              <div className="user-info">
                                <div className="user-name">
                                  {current.subscription_id}
                                </div>
                              </div>
                            </td>
                            <td className="user-email">
                              {formatDate(current.purchasedAt)}
                            </td>
                            <td>
                              <span>{formatDate(current.startTime)}</span>
                            </td>
                            <td>
                              <span>{formatDate(current.endTime)}</span>
                            </td>
                            <td>
                              <span
                                className={`status ${
                                  current.active ? "sub-active" : "sub-expire"
                                }`}
                              >
                                {current.active ? "Active" : "Expired"}
                              </span>
                            </td>
                          </tr>
                        </>
                      );
                    })}
                  </>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default AdminSubscription;
