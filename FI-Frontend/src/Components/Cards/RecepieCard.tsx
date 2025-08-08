import "../../css/PlanCard.css";
import {
  capitalizeSentence,
  convertToMinutesAndSeconds,
} from "../../Types/Helper";
const RecepieCard = ({
  title,
  category,
  createdByUser,
  recepieid,
  duration,
  description,
  thumbnaiUrl,
}: {
  title: string;
  recepieid: number;
  category: string;
  createdByUser: number;
  description: string;
  duration: string;
  thumbnaiUrl: string;
}) => {
  return (
    <>
      <div className="plate-plan-card">
        <div className="plate-plan-image">
          <img src={thumbnaiUrl} alt={title + recepieid} />
        </div>
        <div className="plate-plan-content">
          <h3 className="plate-plan-title">{capitalizeSentence(title)}</h3>
          <p className="plate-plan-desc">
            {description.split(" ").slice(0, 15).join(" ") + "...."}
          </p>
          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <p className="plate-plan-desc">Created By #{createdByUser}</p>

            <p className="plate-plan-desc">{category}</p>
          </div>

          <div className="plate-plan-meta">
            <span className="plate-plan-calories">Duration</span>
            <span className="plate-plan-duration">
              {convertToMinutesAndSeconds(Number(duration))}
            </span>
          </div>
        </div>
      </div>
    </>
  );
};

export default RecepieCard;
