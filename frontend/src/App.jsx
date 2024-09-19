import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { Box, Heading } from '@chakra-ui/react';
import TVSeriesList from './components/TvSeriesList';
import TVSeriesDetail from './components/TvSerieDetail';

function App() {
  return (
    <Router>
      <Box p={8}>
        <Heading mb={6}>TV Shows</Heading>
        <Routes>
          <Route path="/" element={<TVSeriesList />} /> {/* Viser liste over TV-serier */}
          <Route path="/tvserie/:tvShowId" element={<TVSeriesDetail />} /> {/* Viser detaljer for en spesifikk TV-serie */}
        </Routes>
      </Box>
    </Router>
  );
}

export default App;
