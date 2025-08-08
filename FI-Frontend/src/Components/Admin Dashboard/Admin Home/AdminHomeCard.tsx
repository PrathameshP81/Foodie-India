const AdminHomeCard = ({
  name,
  value,
  username,
}: {
  name: string;
  value: string | number;
  username: string;
}) => {
  return (
    <>
      <div className="adminhome-card">
        <div className="adminhome-card-header">
          <div className="adminhome-card-title">{name}</div>
          <div className="adminhome-card-icon">📊</div>
        </div>
        <div className="adminhome-card-value">{value}</div>
        <div className="adminhome-progress-bar">
          <div className="adminhome-progress-fill"></div>
        </div>

        <div className="adminhome-card-footer">{username}</div>
      </div>
    </>
  );
};

export default AdminHomeCard;
