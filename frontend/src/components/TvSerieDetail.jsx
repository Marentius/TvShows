import { useParams } from 'react-router-dom';
import { Box, Heading, Text, Stack, Spinner, Alert, AlertIcon } from '@chakra-ui/react';
import axios from 'axios';
import { useState, useEffect } from 'react';

const TVSeriesDetail = () => {
  const { tvShowId } = useParams();
  const [episodes, setEpisodes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get(`http://localhost:1515/api/tvserie/${tvShowId}/sesong/1`)
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
    return <Spinner size="xl" />;
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
    <Box>
      <Heading>{tvShowId}</Heading>
      <Stack spacing={4} mt={4}>
        {episodes.map(episode => (
          <Box key={episode.episodeNummer} p={3} shadow="md" borderWidth="1px">
            <Text fontWeight="bold">Episode {episode.episodeNummer}: {episode.tittel}</Text>
            <Text>{episode.beskrivelse}</Text>
          </Box>
        ))}
      </Stack>
    </Box>
  );
};

export default TVSeriesDetail;
