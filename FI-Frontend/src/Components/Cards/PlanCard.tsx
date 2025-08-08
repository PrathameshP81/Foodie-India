import "../../css/PlanCard.css";
import { capitalizeSentence, formatDate } from "../../Types/Helper";
import type { PlanType } from "../../Types/Types";

const PlanCard = ({
  title,
  price,
  category,
  description,
  createdAt,
  createdByUser,
  thumbnaiUrl,
}: PlanType) => {
  return (
    <>
      <div className="plate-plan-card">
        <div className="plate-plan-image">
          <img src={thumbnaiUrl} alt={title} />
        </div>
        <div className="plate-plan-content">
          <h3 className="plate-plan-title">{capitalizeSentence(title)}</h3>
          <p className="plate-plan-desc">{description}</p>
          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <p className="plate-plan-desc">Created By #{createdByUser}</p>
            <p className="plate-plan-desc">Price ${price}</p>
            <p className="plate-plan-desc">{category}</p>
          </div>

          <div className="plate-plan-meta">
            <span className="plate-plan-calories">Created At</span>
            <span className="plate-plan-duration">{formatDate(createdAt)}</span>
          </div>
        </div>
      </div>
    </>
  );
};

export default PlanCard;
