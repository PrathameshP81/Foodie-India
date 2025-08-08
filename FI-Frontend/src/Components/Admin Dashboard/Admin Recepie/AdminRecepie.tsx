import { useState } from "react";
import AdminReccepieIngrediants from "./AdminReccepieIngrediants";
import AdminRecepieForm from "./AdminRecepieForm";
import AdminRecepieSteps from "./AdminRecepieSteps";

const AdminRecepie = () => {
  const [ingrediants, setIngrediants] = useState<string[]>([]);
  const [steps, setSteps] = useState<string[]>([]);

  const handleRemove = (operation: string, value: string) => {
    if (operation == "delete ingredients") {
      const filteredIngredient = ingrediants.filter((current) => {
        return current != value;
      });
      setIngrediants(filteredIngredient);
    }

    if (operation == "delete steps") {
      const filteredSteps = steps.filter((current) => {
        return current != value;
      });
      setSteps(filteredSteps);
    }
  };
  return (
    <>
      <div className="rd-container">
        <div className="rd-card rd-card-50">
          <h2>Create New Recepie</h2>
          <AdminRecepieForm
            setIngrediants={setIngrediants}
            setSteps={setSteps}
            ingrediants={ingrediants}
            steps={steps}
          />
        </div>
        <AdminReccepieIngrediants
          ingrediants={ingrediants}
          handleRemove={handleRemove}
        />
        <AdminRecepieSteps steps={steps} handleRemove={handleRemove} />
      </div>
    </>
  );
};

export default AdminRecepie;
