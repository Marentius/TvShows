import { useState, useEffect } from 'react';
import { Box, Heading, Text, Stack, Spinner, Alert, AlertIcon } from '@chakra-ui/react';
import axios from 'axios';

// Komponent for å vise en liste med TV-serier
const TVSeriesList = () => {
  const [tvShows, setTvShows] = useState([]);  // Tilstand for TV-seriene
  const [loading, setLoading] = useState(true);  // Tilstand for lasting
  const [error, setError] = useState(null);  // Tilstand for feil

  // Bruker effect for å hente data fra backend ved første lasting
  useEffect(() => {
    axios.get('http://localhost:1515/api/tvserie')  // Bytt URL hvis det er nødvendig
      .then(response => {
        setTvShows(response.data);
        setLoading(false);  // Sett loading til false når data er hentet
      })
      .catch(err => {
        setError(err.message);  // Håndter feil
        setLoading(false);  // Sett loading til false hvis det oppstår feil
      });
  }, []);

  // Viser en spinner mens data lastes
  if (loading) {
    return <Spinner size="xl" />;
  }

  // Viser en feilmelding hvis noe går galt
  if (error) {
    return (
      <Alert status="error">
        <AlertIcon />
        {error}
      </Alert>
    );
  }

  // Viser TV-serier når de er lastet inn
  return (
    <Stack spacing={4}>
      {tvShows.length === 0 ? (
        <Text>No TV shows found.</Text>
      ) : (
        tvShows.map(tvShow => (
          <Box key={tvShow.title} p={4} shadow="md" borderWidth="1px">
            <Heading fontSize="xl">{tvShow.title}</Heading>
            <Text mt={4}>{tvShow.description}</Text>
          </Box>
        ))
      )}
    </Stack>
  );
};

// Hovedkomponenten for applikasjonen
function App() {
  return (
    <Box p={8}>
      <Heading mb={6}>TV Shows</Heading>
      <TVSeriesList />  {/* Viser listen over TV-serier */}
    </Box>
  );
}

export default App;
