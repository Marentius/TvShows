import { useState, useEffect } from 'react';
import { Box, Heading, Text, Stack, Spinner, Alert, AlertIcon, Image, Button } from '@chakra-ui/react';
import axios from 'axios';

// Komponent for å vise en liste med TV-serier
const TVSeriesList = () => {
  const [tvShows, setTvShows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:1515/api/tvserie')  // Forespørsel til backend for å hente TV-serier
      .then(response => {
        console.log("TV-serier fra backend:", response.data); // Logg dataene fra API
        setTvShows(response.data);
        setLoading(false);  // Sett loading til false når data er hentet
      })
      .catch(err => {
        setError(err.message);  // Håndter feil
        setLoading(false);  // Sett loading til false hvis feil oppstår
      });
  }, []);

  if (loading) {
    return <Spinner size="xl" />;  // Viser en spinner mens data lastes
  }

  if (error) {
    return (
      <Alert status="error">
        <AlertIcon />
        {error}
      </Alert>
    );
  }

  if (tvShows.length === 0) {
    return <Text>No TV shows found.</Text>;
  }

  return (
    <Stack spacing={4}>
      {tvShows.map(tvShow => (
        <Box key={tvShow.tittel} p={4} shadow="md" borderWidth="1px" display="flex" alignItems="center">
          {tvShow.bildeUrl ? (
            <Image src={tvShow.bildeUrl} alt={tvShow.tittel} boxSize="100px" objectFit="cover" mr={4} />
          ) : (
            <Text>No image available</Text>
          )}
          <Box>
            <Heading fontSize="xl">{tvShow.tittel}</Heading>
            <Text mt={4}>{tvShow.beskrivelse}</Text>
            <EpisodesList tvShowId={tvShow.tittel} />  {/* Viser episoder for hver TV-serie */}
          </Box>
        </Box>
      ))}
    </Stack>
  );
};

// Komponent for å vise episoder for en gitt TV-serie
const EpisodesList = ({ tvShowId }) => {
  const [episodes, setEpisodes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get(`http://localhost:1515/api/tvserie/${tvShowId}/sesong/1`)  // Forespørsel til backend for episoder i sesong 1
      .then(response => {
        setEpisodes(response.data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, [tvShowId]);

  if (loading) {
    return <Spinner size="md" />;
  }

  if (error) {
    return (
      <Alert status="error">
        <AlertIcon />
        {error}
      </Alert>
    );
  }

  return (
    <Stack spacing={2} mt={4}>
      {episodes.map(episode => (
        <Box key={episode.episodeNummer} p={3} shadow="md" borderWidth="1px">
          <Text fontWeight="bold">Episode {episode.episodeNummer}: {episode.tittel}</Text>
          <Text>{episode.beskrivelse}</Text>
          {/* Slett episode-knapp */}
          <Button colorScheme="red" mt={2} onClick={() => deleteEpisode(tvShowId, episode.sesongNummer, episode.episodeNummer)}>
            Delete Episode
          </Button>
        </Box>
      ))}
    </Stack>
  );
};

// Funksjon for å slette en episode
const deleteEpisode = (tvShowId, seasonNumber, episodeNumber) => {
    axios.delete(`http://localhost:1515/api/tvserie/${tvShowId}/sesong/${seasonNumber}/episode/${episodeNumber}/delete`)
      .then(() => {
        alert('Episode deleted successfully!');
        window.location.reload();  // Oppdater siden etter sletting
      })
      .catch(err => {
        console.error('Error deleting episode:', err.response);  // Logg responsen ved feil
      });
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
