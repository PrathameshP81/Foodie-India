import AdminPlanAllRecepies from "./AdminPlanAllRecepies";
import AdminPlanForm from "./AdminPlanForm";
import AdminPlanRecepieAddedList from "./AdminPlanAddedRecepieList";
import "../../../css/AdminPlans.css";
import { useState } from "react";
import type { RecepieType } from "../../../Types/Types";
import toast from "react-hot-toast";
const AdminPlans = () => {
  const [Recepie, setRecepie] = useState<RecepieType[]>([]);
  const [Recepieids, setRecepieids] = useState<number[]>([]);

  const handleRecepie = (operation: "add" | "remove", recepie: RecepieType) => {
    if (operation === "add") {
      const isRecepieFound = Recepie.some((r) => r.id === recepie.id);
      if (!isRecepieFound) {
        setRecepie((prev) => [...prev, recepie]);
        setRecepieids((prev) => [...prev, recepie.id]);
        toast.success("Recipe added to plan");
      } else {
        toast.error("Recipe already added to plan");
      }
    } else {
      setRecepie((prev) => prev.filter((r) => r.id !== recepie.id));
      setRecepieids((prev) => prev.filter((id) => id !== recepie.id));
      toast.error("Recipe removed from plan");
    }
  };

  return (
    <div className="rd-container">
      <div className="rd-card rd-card-50">
        <h2>Create New Plan</h2>
        <AdminPlanForm
          Recepieids={Recepieids}
          setRecepie={setRecepie}
          setRecepieids={setRecepieids}
        />
      </div>
      <AdminPlanRecepieAddedList
        handleRecepie={handleRecepie}
        Recepie={Recepie}
      />

      <AdminPlanAllRecepies handleRecepie={handleRecepie} />
    </div>
  );
};

export default AdminPlans;
