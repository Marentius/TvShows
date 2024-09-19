import { useState, useEffect } from 'react';
import { Box, Heading, Text, Flex, Image, Link, Spinner, Alert, AlertIcon } from '@chakra-ui/react';
import { Link as RouterLink } from 'react-router-dom';
import axios from 'axios';

// Komponent for å vise en liste med TV-serier
const TVSeriesList = () => {
  const [tvShows, setTvShows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:1515/api/tvserie')  // Forespørsel til backend for å hente TV-serier
      .then(response => {
        setTvShows(response.data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
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
    <Flex wrap="wrap" justify="space-between" mt={8}>
      {tvShows.map(tvShow => (
        <Link
          as={RouterLink}
          to={`/tvserie/${tvShow.tittel}`}
          key={tvShow.tittel}
          style={{ textDecoration: 'none' }}
          _hover={{ transform: 'scale(1.05)' }}
          _focus={{ boxShadow: 'outline' }}
          w={["100%", "48%", "23%"]}  // Bredden justeres avhengig av skjermstørrelse (mobil, tablet, desktop)
          m={2}
        >
          <Box
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            shadow="md"
            p={4}
            display="flex"
            flexDirection="column"
            alignItems="center"
            justifyContent="center"
            h="400px"  // Fast høyde for å holde boksene jevne
          >
            {tvShow.bildeUrl ? (
              <Image src={tvShow.bildeUrl} alt={tvShow.tittel} boxSize="200px" objectFit="cover" mb={4} />
            ) : (
              <Box boxSize="200px" bg="gray.200" mb={4}>
                <Text>No image available</Text>
              </Box>
            )}
            <Heading as="h3" fontSize="xl" textAlign="center" mb={2}>
              {tvShow.tittel}
            </Heading>
            <Text textAlign="center" noOfLines={3}>  {/* Begrens til 3 linjer for beskrivelse */}
              {tvShow.beskrivelse}
            </Text>
          </Box>
        </Link>
      ))}
    </Flex>
  );
};

export default TVSeriesList;
