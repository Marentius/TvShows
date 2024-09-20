import { useParams } from 'react-router-dom';
import { Box, Heading, Text, Stack, Spinner, Alert, AlertIcon, Image, Select, Button } from '@chakra-ui/react'; // Added necessary imports
import axios from 'axios';
import { useState, useEffect } from 'react';

const TVSeriesDetail = () => {
  const { tvShowId } = useParams();
  const [episodes, setEpisodes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [season, setSeason] = useState(1); // Added state for selected season
  const [numSeasons, setNumSeasons] = useState(1); // Added state for number of seasons
  const [sortCriteria, setSortCriteria] = useState('episodeNummer'); // Added state for sort criteria

  useEffect(() => {
    // Fetch the TV series details including the number of seasons
    axios.get(`http://localhost:1515/api/tvserie/${tvShowId}`)
      .then(response => {
        setNumSeasons(response.data.antallSesonger); // Assuming the response contains the number of seasons
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, [tvShowId]);

  useEffect(() => {
    setLoading(true);
    axios.get(`http://localhost:1515/api/tvserie/${tvShowId}/sesong/${season}`)
      .then(response => {
        setEpisodes(response.data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, [tvShowId, season]); // Added season to dependency array

  const handleSeasonChange = (event) => {
    setSeason(event.target.value);
  };

  const handleSortChange = (event) => {
    setSortCriteria(event.target.value);
    setEpisodes([...episodes].sort((a, b) => a[event.target.value] > b[event.target.value] ? 1 : -1));
  };

  const handleDelete = (episodeNummer) => {
    axios.delete(`http://localhost:1515/api/tvserie/${tvShowId}/sesong/${season}/episode/${episodeNummer}/delete`)
      .then(() => {
        setEpisodes(episodes.filter(episode => episode.episodeNummer !== episodeNummer));
      })
      .catch(err => {
        setError(`Failed to delete episode: ${err.message}`);
      });
  };

  const handleUpdate = (episodeNummer, updatedData) => {
    axios.put(`http://localhost:1515/api/tvserie/${tvShowId}/sesong/${season}/episode/${episodeNummer}`, updatedData)
      .then(response => {
        setEpisodes(episodes.map(episode => episode.episodeNummer === episodeNummer ? response.data : episode));
      })
      .catch(err => {
        setError(`Failed to update episode: ${err.message}`);
      });
  };

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
      <Text>Total Seasons: {numSeasons}</Text> {/* Display the number of seasons */}
      <Select value={season} onChange={handleSeasonChange} mt={4} mb={4}>
        {Array.from({ length: numSeasons }, (_, i) => (
          <option key={i + 1} value={i + 1}>Season {i + 1}</option>
        ))}
      </Select>
      <Select value={sortCriteria} onChange={handleSortChange} mt={4} mb={4}>
        <option value="episodeNummer">Episode Number</option>
        <option value="tittel">Title</option>
        <option value="gjennomsnittligSpilletid">Average Playtime</option> {/* Added sort criteria */}
        {/* Add more sort criteria as needed */}
      </Select>
      <Stack spacing={4} mt={4}>
        {episodes.map(episode => (
          <Box key={episode.episodeNummer} p={3} shadow="md" borderWidth="1px">
            <Text fontWeight="bold">Episode {episode.episodeNummer}: {episode.tittel}</Text>
            <Text>{episode.beskrivelse}</Text>
            <Image src={episode.bildeUrl} alt={`Episode ${episode.episodeNummer} image`} /> {/* Add Image component */}
            <Button colorScheme="red" onClick={() => handleDelete(episode.episodeNummer)}>Delete</Button>
            <Button colorScheme="blue" onClick={() => handleUpdate(episode.episodeNummer, { tittel: 'Updated Title' })}>Update</Button>
          </Box>
        ))}
      </Stack>
    </Box>
  );
};

export default TVSeriesDetail;