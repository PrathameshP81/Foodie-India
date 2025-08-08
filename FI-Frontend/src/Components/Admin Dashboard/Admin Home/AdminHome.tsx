import { useSelector } from "react-redux";
import "../../../css/AdminHome.css";
import type { RootState } from "../../../store/store";
import { formatDate } from "../../../Types/Helper";
import AdminHomeCard from "./AdminHomeCard";
const AdminHome = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);

  const data = [
    {
      name: "Total Plans",
      value: state.user?.createdPlanIds?.length || 0,
    },
    {
      name: "Total Recepies",
      value: state.user?.createdRecepieIds?.length || 0,
    },
    {
      name: "Role",
      value: state.user?.role || "User",
    },
    {
      name: "Data",
      value: formatDate(new Date().toLocaleDateString()),
    },
  ];
  return (
    <>
      <div className="adminhome-header">
        <h1>Admin Dashboard</h1>
      </div>

      <div className="adminhome-grid">
        {data.map((current, index) => {
          return (
            <AdminHomeCard
              key={index}
              name={current.name}
              value={current.value}
              username={state.user?.name!}
            />
          );
        })}
      </div>
    </>
  );
};

export default AdminHome;
{
  /* style="width: 78%; background-color: #3498db;" */
}
