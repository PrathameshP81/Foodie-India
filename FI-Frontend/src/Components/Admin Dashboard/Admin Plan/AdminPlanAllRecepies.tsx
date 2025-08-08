import { useState } from "react";
import { useGetAllRecepieQuery } from "../../../Api/RecepieApi";
import type { RecepieType } from "../../../Types/Types";
import {
  capitalizeSentence,
  convertToMinutesAndSeconds,
} from "../../../Types/Helper";

const AdminPlanAllRecepies = ({
  handleRecepie,
}: {
  handleRecepie: (operation: "add" | "remove", recepie: RecepieType) => void;
}) => {
  const [search, setSearch] = useState("");
  const { data, error } = useGetAllRecepieQuery();
  if (error) return <h1> Something Went Wrong! </h1>;

  return (
    <>
      <div className="rd-card rd-card-25">
        <h2>All Recipes</h2>
        <input
          type="text"
          onChange={(e) => setSearch(e.target.value)}
          className="rd-search-bar"
          placeholder="Search recipes..."
        />
        {data?.data
          .filter((current: RecepieType) =>
            current.title.toLowerCase().includes(search.toLowerCase())
          )
          .map((current) => (
            <div key={current.id} className="rd-recipe-card">
              <div className="rd-recipe-info">
                <div className="rd-recipe-header">
                  <h3>{capitalizeSentence(current.title)}</h3>
                  <button
                    className="rd-add-btn"
                    title="Add Recipe"
                    onClick={() => handleRecepie("add", current)}
                  >
                    ＋
                  </button>
                </div>
                <p>
                  Duration •
                  {convertToMinutesAndSeconds(Number(current.duration))}
                </p>
              </div>
            </div>
          ))}
      </div>
    </>
  );
};

export default AdminPlanAllRecepies;
