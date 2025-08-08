import toast from "react-hot-toast";
import type { SubscriptionType } from "../../Types/Types";
import { useSetOrderMutation } from "../../Api/PlanApi";
import { usePurchaseSubscriptionMutation } from "../../Api/SubscriptionApi";
import { formatDate } from "../../Types/Helper";
import { useSelector } from "react-redux";
import type { RootState } from "../../store/store";

export const SubscriptionCard = ({
  subscription_id,
  subscription_name,
  createdAt,
  subscription_description,
  subscription_price,
  durationInDays,
  createdByUser,
}: SubscriptionType) => {
  const [setOrder] = useSetOrderMutation();
  const [purchaseSubscription] = usePurchaseSubscriptionMutation();
  const state = useSelector((state: RootState) => state.AuthSlice);

  const handlSubscription = async () => {
    try {
      // 1. Create Razorpay order from backend
      const response = await setOrder(subscription_price).unwrap();

      console.log("Razorpay order created:", response);

      // 2. Set Razorpay options
      const options = {
        key: "rzp_test_tznwaW2BNk9zii",
        amount: response.amount,
        currency: "INR",
        name: "Foodie India",
        description: "Subscription Purchase",
        order_id: response.orderId,
        handler: async function (rzpResponse: any) {
          try {
            const planRes = await purchaseSubscription({
              subscriptionId: subscription_id,
              paymentDetails: {
                razorpay_payment_id: rzpResponse.razorpay_payment_id,
                razorpay_order_id: rzpResponse.razorpay_order_id,
              },
            }).unwrap();
            console.log("🎉 Subscription activated:", planRes);
            console.log("Payment success:", rzpResponse);
            toast.success("Subscription Purchased Succcessfully");
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
      <div className="subscription-card">
        <div className="subscription-header">
          <div className="subscription-name">{subscription_name}</div>
          <div className="subscription-price">
            ${subscription_price} <span>/month</span>
          </div>
        </div>
        <div className="subscription-features">
          <p>{subscription_description}</p>

          <div className="feature">
            <i>✓</i>Subscription #{subscription_id}
          </div>
          <div className="feature">
            <i>✓</i> Duration : {durationInDays} Days
          </div>
          <div className="feature">
            <i>✓</i> Created At : {formatDate(createdAt)}
          </div>
          <div className="feature">
            <i>✓</i> Created By : #{createdByUser}
          </div>
        </div>
        <button className="subscription-button" onClick={handlSubscription}>
          Get Started
        </button>
      </div>
    </>
  );
};

export default SubscriptionCard;
