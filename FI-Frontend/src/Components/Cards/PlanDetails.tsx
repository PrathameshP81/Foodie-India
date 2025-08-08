import { useNavigate, useParams } from "react-router-dom";
import "../../css/PlanDetails.css";
import toast from "react-hot-toast";
import { useDispatch, useSelector } from "react-redux";
import type { RootState } from "../../store/store";
import {
  useGetPlanByIdQuery,
  usePurchasePlanMutation,
  useSetOrderMutation,
} from "../../Api/PlanApi";
import { capitalizeSentence, formatDate } from "../../Types/Helper";
import { setUpdatedPlanData } from "../../Reducers/SetUpdatedDataSlice";
import type { RecepieType } from "../../Types/Types";

const PlanDetails = () => {
  const { id } = useParams();
  const state = useSelector((state: RootState) => state.AuthSlice);

  const { data, isLoading, error } = useGetPlanByIdQuery(Number(id));
  const [setOrder] = useSetOrderMutation();
  const [purchasePlan] = usePurchasePlanMutation();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const isPlanPurchased = state.user?.purchasedPlanIds?.includes(Number(id));

  if (!state.isLoggedIn) {
    navigate("/error");
  }

  if (error) {
    return (
      <h1> Oops ! Something Went Wrong , Failed to Connect to Server... </h1>
    );
  }

  const handlePurchase = async (amount: number, planid: number) => {
    try {
      const response = await setOrder(amount).unwrap();

      console.log("Razorpay order created:", response);

      const options = {
        key: "rzp_test_tznwaW2BNk9zii",
        amount: response.amount,
        currency: "INR",
        name: "Foodie India",
        description: "Plan Purchase",
        order_id: response.orderId,
        handler: async function () {
          try {
            const planRes = await purchasePlan(planid).unwrap();
            console.log("Purchsed Plan", planRes);
            toast.success("Plan Purchased Succcessfully");
          } catch (error: any) {
            toast.error(error?.data?.message || "Something Went Wrong... ");
          }
        },
        prefill: {
          name: state.user?.name,
          email: state.user?.email,
          contact: state.user?.phone,
        },
        theme: {
          color: "#3399cc",
        },
      };

      const rzp = new (window as any).Razorpay(options);

      rzp.open();
    } catch (err) {
      console.error("Error creating order:", err);
    }
  };

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
              <img
                src={data?.data.thumbnaiUrl}
                alt="Healthy meal plan image showing colorful bowls of nutritious food with grains, vegetables and lean proteins arranged artistically"
              />
            </div>

            <div className="single-info-column">
              <div className="plan-details-column">
                <h2 className="single-plan-title">
                  {capitalizeSentence(data?.data.title!)}
                </h2>
                <p className="single-plan-description">
                  {data?.data.description}
                </p>
                <ul className="single-plan-features">
                  <li>
                    Created At : {formatDate(String(data?.data.createdAt))}
                  </li>
                  <li>Created By #{data?.data.createdByUser}</li>
                  <li>Total Recepies : {data?.data.recepies?.length}</li>
                  <li>Category : {data?.data.category}</li>
                  <li>
                    <strong>Price : ${data?.data.price}</strong>
                  </li>
                </ul>
                {state?.user?.role != "ROLE_ADMIN" ? (
                  <>
                    <button
                      className="plan-details-card-btn"
                      onClick={() => {
                        handlePurchase(data?.data.price!, data?.data.planid!);
                      }}
                      disabled={isPlanPurchased}
                    >
                      {isPlanPurchased ? "Already Purchased" : "Purchase Plan"}
                    </button>
                  </>
                ) : (
                  <></>
                )}
              </div>

              <div className="recipe-list-column">
                <div className="heading-row">
                  <h3 className="single-recipe-heading">Included Recipes</h3>
                  {state.user?.role == "ROLE_ADMIN" ? (
                    <button
                      className="icon-button"
                      title="Update Recipes"
                      onClick={() => {
                        dispatch(
                          setUpdatedPlanData({
                            title: data?.data.title,
                            category: data?.data.category,
                            description: data?.data.description,
                            price: data?.data.price,
                            recepies: data?.data.recepies,
                            planid: data?.data.planid,
                          })
                        );
                        navigate("/dashboard/plan", { replace: true });
                      }}
                    >
                      🖉
                    </button>
                  ) : (
                    ""
                  )}
                </div>

                <div className="scrollable-recipe-list">
                  <ul>
                    {data?.data.recepies?.map((current: RecepieType) => (
                      <li key={current.id}>
                        <img
                          src={current.imageUrl}
                          alt={current.title}
                          className="recepie-profile-image"
                        />
                        <span className="recipe-title">
                          {capitalizeSentence(current.title)}
                        </span>
                      </li>
                    ))}
                  </ul>
                </div>
              </div>
            </div>
          </>
        )}
      </div>
    </>
  );
};

export default PlanDetails;
