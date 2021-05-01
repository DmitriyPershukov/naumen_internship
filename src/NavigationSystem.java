import java.util.*;

public class NavigationSystem implements RouteFinder
{
    @Override
    public char[][] findRoute(char[][] map) {
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate start = new Coordinate(0,0);
        Map<Coordinate, Coordinate> routeList = new HashMap<Coordinate, Coordinate>();

        Coordinate finish = new Coordinate(0, 0);
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                if  (map[i][j] == '@')
                {
                    start = new Coordinate(i, j);
                }
                if (map[i][j] == 'X')
                {
                    finish = new Coordinate(i, j);
                }
            }
        }
        coordinateQueue.add(start);
        boolean found = false;
        while (coordinateQueue.size() != 0)
        {
            Coordinate coordinate = coordinateQueue.poll();
            if (map[coordinate.x][coordinate.y] == 'X')
            {
                found = true;
                finish = coordinate;
                break;
            }
            List<Coordinate> adjasentCoordinates = new ArrayList<>();
            adjasentCoordinates.add(new Coordinate(coordinate.x, coordinate.y + 1));
            adjasentCoordinates.add(new Coordinate(coordinate.x -1, coordinate.y));
            adjasentCoordinates.add(new Coordinate(coordinate.x +1, coordinate.y));

            adjasentCoordinates.add(new Coordinate(coordinate.x, coordinate.y -1));

            adjasentCoordinates.forEach((y) -> visit(y, map, coordinateQueue, routeList, coordinate));
        }
        if (found)
        {
            Coordinate currentCoordinate = finish;
            while(currentCoordinate != start)
            {
                currentCoordinate = routeList.get(currentCoordinate);
                if(!(map[currentCoordinate.x][currentCoordinate.y] == '@')) {
                    map[currentCoordinate.x][currentCoordinate.y] = '+';
                }
            }
            for (int i = 0; i < map.length; i ++) {
                for (int j = 0; j < map[i].length; j++)
                {
                    if(map[i][j] == 'V')
                    {
                        map[i][j] = '.';
                    }
                }
            }
            return map;
        }
        else
        {
            return null;
        }

    }
    public boolean canBeVisited(Coordinate coordinate, char[][] map)
    {
        if (coordinate.x < map.length && coordinate.x >= 0 && coordinate.y < map[0].length && coordinate.y >= 0)
        {
            if (map[coordinate.x][coordinate.y] != '#' && map[coordinate.x][coordinate.y] != 'V')
            {
                return true;
            }
            return false;
        }

        return false;
    }
    public void visit(Coordinate coordinate, char[][] map, Queue<Coordinate> queue, Map<Coordinate, Coordinate> route, Coordinate currentCoordinate) {
        if (canBeVisited(coordinate, map)) {
            if(!(map[coordinate.x][coordinate.y] == 'X' || map[coordinate.x][coordinate.y] == '@')) {
                map[coordinate.x][coordinate.y] = 'V';
            }
            queue.add(coordinate);
            route.put(coordinate, currentCoordinate);
        }
    }
}