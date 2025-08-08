const AdminReccepieIngrediants = ({
  ingrediants,
  handleRemove,
}: {
  ingrediants: string[];
  handleRemove: (operation: string, value: string) => void;
}) => {
  return (
    <div className="rd-card rd-card-25">
      <h2>Incrediants</h2>
      {ingrediants.map((current: string) => {
        return (
          <>
            <div className="rd-recipe-card">
              <div className="rd-recipe-info">
                <div
                  style={{
                    display: "flex",
                    alignItems: "start",
                    justifyContent: "space-between",
                  }}
                >
                  <p style={{ textAlign: "justify", marginRight: "10px" }}>
                    {current}
                  </p>
                  <button
                    className="rd-remove-btn"
                    title="Remove Recipe"
                    onClick={() => {
                      handleRemove("delete ingredients", current);
                    }}
                  >
                    ✖
                  </button>
                </div>
              </div>
            </div>
          </>
        );
      })}
    </div>
  );
};

export default AdminReccepieIngrediants;
