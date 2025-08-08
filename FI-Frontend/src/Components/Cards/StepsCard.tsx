const StepsCard = ({ steps }: { steps: string[] }) => {
  return (
    <>
      <div className="recipe-list-column">
        <div
          className="recipedashboard-steps-section"
          style={{ overflowY: "auto" }}
        >
          <h2 className="recipedashboard-section-title">Preparation Steps</h2>
          <ol className="recipedashboard-step-list">
            {steps.map((current, index) => {
              return <li key={index}> {current} </li>;
            })}
          </ol>
        </div>
      </div>
    </>
  );
};

export default StepsCard;
