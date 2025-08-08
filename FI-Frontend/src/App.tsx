import { createBrowserRouter, RouterProvider } from "react-router-dom";
import useAuthCheck from "./Components/Helpers/useAuthCheck";
import AppLayout from "./AppLayout";
import Home from "./Pages/Home";
import Register from "./Pages/Register";
import Login from "./Pages/Login";
import Recepies from "./Pages/Recepies";
import Plans from "./Pages/Plans";
import MyPlans from "./Pages/MyPlans";
import PurchasedPlanDashboard from "./Pages/PurchasedPlanDashboard";
import Subscription from "./Pages/Subscription";
import AdminDashboard from "./Pages/AdminDashboard";
import ErrorPage from "./Pages/ErrorPage";
import Logout from "./Pages/Logout";
import { Toaster } from "react-hot-toast";
import AdminHome from "./Components/Admin Dashboard/Admin Home/AdminHome";
import AdminPlans from "./Components/Admin Dashboard/Admin Plan/AdminPlans";
import AdminRecepie from "./Components/Admin Dashboard/Admin Recepie/AdminRecepie";
import AdminSubscription from "./Components/Admin Dashboard/AdminSubscription";
import RecepieDetails from "./Components/Cards/RecepieDetails";
import PlanDetails from "./Components/Cards/PlanDetails";

function App() {
  useAuthCheck();

  const router = createBrowserRouter([
    {
      path: "/",
      element: <AppLayout />,

      children: [
        {
          path: "/",
          element: <Home />,
        },
        {
          path: "/register",
          element: <Register />,
        },
        {
          path: "/login",
          element: <Login />,
        },
        {
          path: "/recepies",
          element: <Recepies />,
        },
        {
          path: "/recepies/:id",
          element: <RecepieDetails />,
        },
        {
          path: "/plans",
          element: <Plans />,
        },
        {
          path: "/plans/:id",
          element: <PlanDetails />,
        },
        {
          path: "/myplans",
          element: <MyPlans />,
        },
        {
          path: "/myplans/:id",
          element: <PurchasedPlanDashboard />,
        },
        {
          path: "/subscription",
          element: <Subscription />,
        },
        {
          path: "/dashboard",
          element: <AdminDashboard />,
          children: [
            {
              path: "home",
              element: <AdminHome />,
            },
            {
              path: "plan",
              element: <AdminPlans />,
            },
            {
              path: "recepie",
              element: <AdminRecepie />,
            },
            {
              path: "subscription",
              element: <AdminSubscription />,
            },
          ],
        },
        {
          path: "/logout",
          element: <Logout />,
        },
        {
          path: "*",
          element: <ErrorPage />,
        },
      ],
    },
  ]);

  return (
    <>
      <RouterProvider router={router} />;
      <Toaster position="top-right" reverseOrder={false} />
    </>
  );
}
export default App;
