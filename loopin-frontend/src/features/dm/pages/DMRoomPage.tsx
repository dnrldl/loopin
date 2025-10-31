import { useParams } from 'react-router-dom';

const DMRoomPage = () => {
  const params = useParams();
  console.log(params.chatId);

  return <div>DMRoomPage</div>;
};

export default DMRoomPage;
