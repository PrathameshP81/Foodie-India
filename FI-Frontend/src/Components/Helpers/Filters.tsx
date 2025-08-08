import { useEffect, useState, type ChangeEvent } from "react";
import { useDispatch } from "react-redux";
import { useGetAllUsersQuery } from "../../Api/UserApi";
import type { UserType } from "../../Types/Types";
import { applyFilters, resetFilters } from "../../Reducers/FilterSlice";

const Filters = () => {
  const dispatch = useDispatch();

  const [filters, setFilters] = useState({
    searchQuery: "",
    category: "All",
    createdBy: "All",
    price: 1000,
  });

  const { data: users } = useGetAllUsersQuery();

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const name = e.target.name;
    const value = e.target.value;

    setFilters({ ...filters, [name]: value });
    dispatch(applyFilters({ ...filters, [name]: value }));
  };

  useEffect(() => {
    dispatch(applyFilters(filters));
  }, []);

  return (
    <>
      <div className="plate-filter-card">
        <h2 className="plate-filter-header">Filter Plans</h2>

        <div className="plate-filter-group">
          {/* <!-- Search Bar --> */}
          <div className="search-bar">
            <input
              type="text"
              name="searchQuery"
              placeholder="Search plans..."
              onChange={handleChange}
            />
          </div>

          {/* <!-- Category Filter --> */}
          <div>
            <h3 className="plate-filter-title">Category</h3>

            <div className="plate-option">
              <input
                type="radio"
                onChange={handleChange}
                value="All"
                id="all"
                name="category"
                checked={filters.category === "All" ? true : false}
              />
              <label htmlFor="all">All</label>
            </div>

            <div className="plate-option">
              <input
                onChange={handleChange}
                type="radio"
                value="VEG"
                id="veg"
                name="category"
                checked={filters.category === "VEG" ? true : false}
              />
              <label htmlFor="veg">Vegetarian</label>
            </div>
            <div className="plate-option">
              <input
                type="radio"
                id="nonveg"
                value="NONVEG"
                onChange={handleChange}
                name="category"
                checked={filters.category === "NONVEG" ? true : false}
              />
              <label htmlFor="nonveg"> Non Vegetarian</label>
            </div>
          </div>

          {/* <!-- Filter by Created User --> */}
          <div>
            <h3 className="plate-filter-title">Created By</h3>
            <select
              className="user-select"
              name="createdBy"
              onChange={handleChange}
            >
              <option value="All">All Users</option>
              {users?.data.map((current: UserType) => {
                return (
                  <>
                    {current.role === "ROLE_ADMIN" ? (
                      <option value={current.userId}>{current.name}</option>
                    ) : (
                      ""
                    )}
                  </>
                );
              })}
            </select>
          </div>

          {/* <!-- Price Range Filter --> */}
          <div>
            <h3 className="plate-filter-title">Price Range</h3>
            <input
              type="range"
              min="0"
              max="1000"
              name="price"
              onChange={handleChange}
            />
            <div className="price-range-labels">
              <span>$0</span>
              <span>${filters.price}</span>
              <span>$1000</span>
            </div>
          </div>
          <div>
            <button
              className="reset-btn"
              onClick={() => {
                dispatch(resetFilters());
              }}
            >
              Reset Filters
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default Filters;
