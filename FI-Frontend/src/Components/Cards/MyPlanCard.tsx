import "../../css/MyPlanCard.css";

type singlePlanType = {
  description: string;
  thumbnaiUrl: string;
  title: string;
  price: number;
};
const MyPlanCard = ({
  description,
  thumbnaiUrl,
  title,
  price,
}: singlePlanType) => {
  return (
    <>
      <div className="dashboard-plan-card">
        <div className="dashboard-plan-image">
          <img src={thumbnaiUrl} alt="Premium plan" />
        </div>
        <div className="dashboard-plan-content">
          <h3 className="dashboard-plan-title">{title}</h3>
          <p className="dashboard-plan-description">{description}</p>

          <div className="dashboard-plan-footer">
            <span className="dashboard-plan-price">${price}</span>
            <span className="dashboard-plan-status dashboard-status-active">
              Active
            </span>
          </div>
        </div>
      </div>
    </>
  );
};

export default MyPlanCard;
