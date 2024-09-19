import { Box, Image, Heading, Text } from '@chakra-ui/react';
import { Link as RouterLink } from 'react-router-dom';
import { Link } from '@chakra-ui/react';

const TVShowCard = ({ tvShow }) => {
  return (
    <Box borderWidth="1px" borderRadius="lg" overflow="hidden">
      <Image src={tvShow.bildeUrl} alt={tvShow.tittel} boxSize="200px" objectFit="cover" />
      <Box p="6">
        <Heading fontSize="xl">
          <Link as={RouterLink} to={`/tvserie/${tvShow.tittel}`}>
            {tvShow.tittel}
          </Link>
        </Heading>
        <Text mt="2">{tvShow.beskrivelse}</Text>
      </Box>
    </Box>
  );
};

export default TVShowCard;
