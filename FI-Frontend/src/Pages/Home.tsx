import { useSelector } from "react-redux";
import mainimage from "../assets/mainimage.png";

import "../css/Home.css";
import type { RootState } from "../store/store";
import { capitalizeSentence } from "../Types/Helper";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const state = useSelector((state: RootState) => state.AuthSlice);
  const navigate = useNavigate();
  return (
    <>
      <div className="home-container">
        {/* Left: Image */}
        <div className="home-image-section">
          <img src={mainimage} alt="Foodie India" className="home-image" />
        </div>

        {/* Right: Content */}

        <div className="home-content-section">
          <h1 className="home-title">
            Welcome{" "}
            {state.isLoggedIn ? capitalizeSentence(state.user?.name!) : "User"}
          </h1>
          <p className="home-tagline">Where Cooking Meets Passion...</p>
          <p className="home-description">
            Foodie India is your ultimate destination to explore, learn, and
            master the art of cooking. Whether you're a home chef or a culinary
            expert, our platform connects you to online classes, curated
            recipes, and expert cooking content — all in one place.
          </p>

          <div className="home-buttons">
            <button
              className="btn"
              onClick={() =>
                state.isLoggedIn
                  ? navigate("/plans", { replace: true })
                  : navigate("/login", { replace: true })
              }
            >
              Explore Plans
            </button>
          </div>

          <ul className="feature-list">
            <li>👨‍🍳 Expert-led Online Plans</li>
            <li>📚 Easy-to-follow Recipes</li>
            <li>🧑‍💻 User & Admin Dashboard</li>
            <li>🎥 Video Tutorials by Chefs</li>
          </ul>
        </div>
      </div>
    </>
  );
};

export default Home;
