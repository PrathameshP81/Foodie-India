import {
  capitalizeSentence,
  convertToMinutesAndSeconds,
} from "../../../Types/Helper";
import type { RecepieType } from "../../../Types/Types";

const AdminPlanRecepieAddedList = ({
  handleRecepie,
  Recepie,
}: {
  handleRecepie: (operation: "add" | "remove", recepie: RecepieType) => void;
  Recepie: RecepieType[];
}) => {
  // const [Recepie, setRecepie] = useState<RecepieType[]>([]);
  // const [Recepieids, setRecepieids] = useState<number[]>([]);
  // const state = useSelector((state: RootState) => state.setUpdatedDataSlice);

  return (
    <>
      <div className="rd-card rd-card-25">
        <h2>Plan Recipes</h2>
        {Recepie.length <= 0 ? (
          <p>No Recipes Added...</p>
        ) : (
          Recepie.map((current: RecepieType) => (
            <div key={current.id} className="rd-recipe-card">
              <div className="rd-recipe-info">
                <div className="rd-recipe-header">
                  <h3>{capitalizeSentence(current.title)}</h3>
                  <button
                    className="rd-remove-btn"
                    title="Remove Recipe"
                    onClick={() => handleRecepie("remove", current)}
                  >
                    ✖
                  </button>
                </div>
                <p>
                  Duration •{" "}
                  {convertToMinutesAndSeconds(Number(current.duration))}
                </p>
              </div>
            </div>
          ))
        )}
      </div>
    </>
  );
};

export default AdminPlanRecepieAddedList;
