const IngredientsCard = ({ ingrediants }: { ingrediants: string[] }) => {
  return (
    <>
      <div
        className="recipedashboard-ingredients-section"
        style={{ overflowY: "auto" }}
      >
        <h2 className="recipedashboard-section-title">Ingredients</h2>
        <ul className="recipedashboard-ingredient-list">
          {ingrediants.map((current: string, index) => {
            return <li key={index}> {current} </li>;
          })}
        </ul>
      </div>
    </>
  );
};

export default IngredientsCard;
